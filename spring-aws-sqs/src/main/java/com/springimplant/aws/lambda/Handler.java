package com.springimplant.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.polly.PollyAsyncClient;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Handler implements RequestHandler<Map<String, String>, String> {

    // Set Environment Variables:
    // MP3_BUCKET_NAME - Name of the S3 bucket to store MP3 files
    // TABLE_NAME - Name of the DynamoDB table containing notes
    // VOICE_ID - Default voice ID for Polly (e.g., Joanna, Matthew)
    // export ENV_PARAMS='TABLE_NAME=Notes, MP3_BUCKET_NAME=pollynotesapibucket'

    private static final Logger logger = LoggerFactory.getLogger(Handler.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final PollyAsyncClient pollyclient = PollyAsyncClient.builder()
            .credentialsProvider(ProfileCredentialsProvider.create())
            .region(Region.of("ap-northeast-1"))
            .build();
    private static final S3AsyncClient s3client = S3AsyncClient.builder()
            .credentialsProvider(ProfileCredentialsProvider.create())
            .region(Region.of("ap-northeast-1"))
            .build();

    private static String getNoteText(String tableName, String userName, String noteId) {
        DynamoDbAsyncClient dbAsyncClient = DynamoDbAsyncClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.of("ap-northeast-1"))
                .build();
        var key = new HashMap<String, AttributeValue>();
        key.put("UserId", AttributeValue.builder().s(userName).build());
        key.put("NoteId", AttributeValue.builder().n(noteId).build());
        var request = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();
        var queryResponse = dbAsyncClient.getItem(request).join();
        return queryResponse.item().get("Note").s();
    }

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
        // This function does the following:
        // 1. Takes a JSON payload from source
        // 2. Calls DynamoDB to fetch the note text from the userId and noteId
        // 3. Calls the Polly synthesize_speech API to convert text to speech
        // 4. Stores the resulting audio in an MP3 file in /tmp
        // 5. Uploads the MP3 file to S3
        // 6. Creates a pre-signed URL for the MP3 file
        // 7. Returns the URL as response object

        String response;
        try {
            logger.info("environment: {}", gson.toJson(System.getenv()));
            logger.info("event: {}", gson.toJson(event));
            logger.info("context: {}", gson.toJson(context));

            String noteId = event.get("NoteId");
            String voice = event.get("VoiceId");
            String userId = event.get("UserId");
            String bucket = System.getenv("MP3_BUCKET_NAME");
            String tableName = System.getenv("TABLE_NAME");

            logger.info("id: {}", noteId);
            logger.info("voice: {}", voice);
            logger.info("user: {}", userId);
            logger.info("bucket name: {}", bucket);
            logger.info("table name: {}", tableName);

            // Calls DynamoDB to fetch the note text from the userId and noteId
            String noteText = getNoteText(tableName, userId, noteId);
            logger.info("noteText: {}", noteText);

            Path destfile = Path.of(System.getProperty("java.io.tmpdir"),
                    userId + "-" + noteId + ".mp3");
            try {
                Files.deleteIfExists(destfile);
            } catch (IOException e) {
                logger.error("could not delete temp file - trying to move ahead anyway");
            }

            // Calls the Polly synthesize_speech API to convert text to speech
            // Stores the resulting audio in an MP3 file in /tmp
            pollyclient.synthesizeSpeech(SynthesizeSpeechRequest.builder()
                            .outputFormat("mp3")
                            .text(noteText)
                            .voiceId(voice)
                            .build(), destfile)
                    .join();


            var key = userId + "/" + noteId + ".mp3";

            s3client.putObject(PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType("audio/mpeg")
                            .build(), destfile)
                    .join();

            logger.info("Polly synth and S3 put object complete");

            // Creates a pre-signed URL for the MP3 file
            S3Presigner presigner = S3Presigner.builder().build();
            URL url = presigner.presignGetObject(GetObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(5))
                            .getObjectRequest(GetObjectRequest.builder()
                                    .bucket(bucket)
                                    .key(key)
                                    .build())
                            .build())
                    .url();
            response = url.toString();
            logger.info("generated url {}", response);

        } catch (Exception e) {
            logger.warn("Exception thrown from dictate handler");
            throw e;
        }
        return response;
    }
}
