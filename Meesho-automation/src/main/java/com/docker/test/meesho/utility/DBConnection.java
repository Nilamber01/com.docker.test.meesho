package com.docker.test.meesho.utility;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBConnection {
    
    public static void connectToMongoDB() {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("employee_db");
        MongoCollection<Document> collection = database
                .getCollection("employees");
 
        List<Document> documents = (List<Document>) collection.find().into(
                new ArrayList<Document>());
 
               for(Document document : documents){
                   System.out.println(document);
               }
    }

}
