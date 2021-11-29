package zure.service;

import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import zure.Executable;
import zure.data.TargetData;

public class MongoDbService implements Executable {

    @Override
    public List<String> execute(Object connection, TargetData loadedData) throws Exception {

        // jdbc:mongodb:Server=MyServer;Port=27017;Database=test;User=test;Password=Password;
        connection = MongoClients.create(loadedData.url);
        MongoDatabase db = ((MongoClient) connection).getDatabase("");
        MongoCollection<Document> coll = db.getCollection(loadedData.table);

        // TODO Auto-generated method stub
        return null;
    }

}