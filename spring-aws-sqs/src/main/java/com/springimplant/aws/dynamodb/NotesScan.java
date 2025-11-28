package com.springimplant.aws.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.springimplant.aws.common.getInputs;

import java.util.Iterator;

public class NotesScan {

    //Create DynamoDB client and use Document API wrapper
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);
    static getInputs config = new getInputs();

    public static void main(String[] args) throws Exception {

        //Initialize the Read operations inputs
        String searchText = config.getSearchText();

        //Get Notes table information

        Table table = dynamoDB.getTable(config.getTableName());

        scanAllNotesForTextPaginator(table, searchText);

    }

    private static void scanAllNotesForTextPaginator(Table table, String searchText) {

        //Build request: Scan request with filter expression for a search text in Note

        System.out.format("\n \n Scan table to list items with search text \"%s\" as part of the note:\n", searchText);

        //Build Scan specification with Filter expression, Values, list of attributes to project

        ScanSpec scanSpec = new ScanSpec()
                .withFilterExpression("contains (Note, :v_txt)")
                .withValueMap(new ValueMap().withString(":v_txt", searchText))
                .withProjectionExpression("UserId, NoteId, Note");

        //Limit the response Page size
        scanSpec.withMaxPageSize(1);

        //Run scan table using above specifications
        ItemCollection<ScanOutcome> items = table.scan(scanSpec);

        System.out.println("\nSCANNING TABLE...");

        // Process each page of results
        int pageNum = 0;
        for (Page<Item, ScanOutcome> page : items.pages()) {
            System.out.println("\nPage: " + ++pageNum);
            for (Item value : page) {
                System.out.println(value.toJSONPretty());
            }
        }
    }
}