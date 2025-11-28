package com.springimplant.aws.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class NotesCrudMapper {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .build();


    public static void main(String[] args) {

        // Define a DynamoDB mapper to associate to the instance of NotesItem class
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        testCRUDOperations(mapper);
        System.out.println("CRUD operations complete!");

        testPutWithConditionalUpdate(mapper);
    }

    private static void testCRUDOperations(DynamoDBMapper mapper) {

        // Instantiate NotesItem class to maps to the DynamoDB table. Definition of the class is partially provided to you
        NotesItems item = new NotesItems();

        // Set attribute values to UserId, NoteId, and Note using class methods
        item.setUserId("testuser");
        item.setNoteId(1);
        item.setNotes("this is my very first note");

        try {
            // Save the item (note) to Note Table.
            mapper.save(item);

            // Retrieve the item from Notes
            NotesItems itemRetrieved = mapper.load(NotesItems.class, "testuser", 1);
            System.out.println("Item retrieved:");
            System.out.println(itemRetrieved);

            // Update the item in Notes
            itemRetrieved.setNotes("updated notes");
            // Save the updated attributes
            mapper.save(itemRetrieved);

            System.out.println("Item updated:");
            System.out.println(itemRetrieved);

            // Retrieve the updated item.
            //Change mapper configuration to set read consistency
            DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                    .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                    .build();

            NotesItems updatedItem = mapper.load(NotesItems.class, "testuser", 1, config);
            System.out.println("Retrieved the previously updated item:");
            System.out.println(updatedItem);

            // Delete the item from Notes
            NotesItems deleteItem = mapper.load(NotesItems.class, "testuser", 1, config);
            mapper.delete(deleteItem);

            System.out.println("deleting the previously existing item:");
            System.out.println(deleteItem);

            // Try to retrieve deleted item.
            NotesItems deletedItem = mapper.load(NotesItems.class, deleteItem.getUserId(), deleteItem.getNoteId(), config);
            if (deletedItem == null) {
                System.out.println("Done - Sample item is deleted.");
            }
        } catch (AmazonDynamoDBException e) {
            System.err.println(e.getMessage());
            e.getStackTrace();
        } catch (DynamoDBMappingException ddbme) {
            System.err.println("Client side error in Mapper, fix before retrying. Error: " + ddbme.getMessage());
        } catch (Exception ex) {
            System.err.println("An exception occurred, investigate and configure retry strategy. Error: " + ex.getMessage());
        }
    }

    private static void testPutWithConditionalUpdate(DynamoDBMapper mapper) {

        NotesItems item = new NotesItems();

        // Retrieve the item to update
        NotesItems itemRetrieved = mapper.load(NotesItems.class, "newbie", 1);
        System.out.println("Item retrieved:");
        System.out.println(itemRetrieved);

        //Set new values
        item.setUserId(itemRetrieved.getUserId());
        item.setNoteId(itemRetrieved.getNoteId());
        item.setNotes("free swag registration code " + ThreadLocalRandom.current().nextInt());

        ArrayList<AttributeValue> list = new ArrayList<AttributeValue>();
        list.add(new AttributeValue().withS(itemRetrieved.getNotes()));

        Map<String, ExpectedAttributeValue> expectedAttributes = new HashMap<String, ExpectedAttributeValue>();

        ExpectedAttributeValue expectedAttributeValue = new ExpectedAttributeValue()
                .withAttributeValueList(list)
                .withComparisonOperator("EQ");

        expectedAttributes.put("Note", expectedAttributeValue);

        // Update item using withExpected expression
        mapper.save(item, new DynamoDBSaveExpression().withExpected(expectedAttributes));

        NotesItems itemUpdated = mapper.load(NotesItems.class, "newbie", 1);
        System.out.println("Item updated with new notes:");
        System.out.println(itemUpdated);
    }

    // Define DynamoDB Table annotation to maps NotesItems class to DynamoDB table name Notes
    @DynamoDBTable(tableName = "Notes")
    public static class NotesItems {

        //Set up Data Members that correspond to columns in the Music table
        @Setter
        private String UserId;
        @Setter
        private Integer NoteId;
        private String Note;

        // Define the primary key annotation on the attribute UserId
        @DynamoDBHashKey(attributeName = "UserId")
        public String getUserId() {
            return this.UserId;
        }

        // Define the sort key annotation on the attribute NoteId
        @DynamoDBRangeKey(attributeName = "NoteId")
        public Integer getNoteId() {
            return this.NoteId;
        }

        // Define an optional attribute annotation for Note
        @DynamoDBAttribute(attributeName = "Note")
        public String getNotes() {
            return this.Note;
        }

        public void setNotes(String Note) {
            this.Note = Note;
        }

        @Override
        public String toString() {
            return "Notes [User=" + UserId + ", Note Id=" + NoteId + ", Notes=" + Note + "]";
        }
    }
}
