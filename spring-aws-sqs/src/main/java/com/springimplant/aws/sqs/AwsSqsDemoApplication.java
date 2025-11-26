package com.springimplant.aws.sqs;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@SpringBootApplication
public class AwsSqsDemoApplication {
	
	private static final Logger log = LoggerFactory.getLogger(AwsSqsDemoApplication.class);
	
	@PostConstruct
	public void initAws() {
		String bucketToDelete = "labstack-b83b31c4-bc39-4433-a657-ca7e20-lab1bucket-ln78cly5erx9";
		String roleARNString = "arn:aws:iam::395527390279:role/av-sptm-console-role";
		String roleSessionName = "Session_1";
		Regions region = Regions.US_EAST_1;
		AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard().build();
		AssumeRoleRequest roleRequest = new AssumeRoleRequest().withRoleArn(roleARNString).withRoleSessionName(roleSessionName).withDurationSeconds(3600);
		AssumeRoleResult assumeRoleResult = stsClient.assumeRole(roleRequest);
		Credentials credentials = assumeRoleResult.getCredentials();
		log.info("Access Key "+credentials.getAccessKeyId());
		log.info("Secret Access Key "+credentials.getSecretAccessKey());
		log.info("Session Token "+credentials.getSessionToken());
		AWSCredentials awscredentials = new BasicSessionCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey(),credentials.getSessionToken());
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awscredentials);
		AmazonIdentityManagement clientAmazonIdentityManagement = AmazonIdentityManagementClientBuilder.standard().withRegion(region).withCredentials(credentialsProvider).build();
		clientAmazonIdentityManagement.listRoles().getRoles().forEach(r -> log.info(r.getArn()));
	}

	public static void main(String[] args) {
		SpringApplication.run(AwsSqsDemoApplication.class, args);
	}

}
