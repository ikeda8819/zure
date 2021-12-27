package zure.service;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import zure.Executable;
import zure.data.TargetData;

public class MongoDbService implements Executable {

    @Override
    public List<String> execute(TargetData loadedData) throws Exception {

        // jdbc:mongodb:Server=MyServer;Port=27017;Database=test;User=test;Password=Password;
        try (MongoClient connection = MongoClients.create(loadedData.url);) {
            MongoDatabase db = connection.getDatabase(loadedData.database);
            MongoCollection<Document> collection = db.getCollection(loadedData.table);

            MongoCursor<Document> mongoCursor = collection.find().iterator();

            List<String> list = new ArrayList<>();
            try {
                while (mongoCursor.hasNext()) {
                    System.out.println("mongodb.next()mongodb.next()mongodb.next()mongodb.next()");
                    System.out.println(mongoCursor.next());
                }
            } catch (Exception e) {
                list.clear();
            } finally {
                if (mongoCursor != null)
                    mongoCursor.close();
            }
            return list;
        }
    }

}
