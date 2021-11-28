package zure.service;

import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import zure.Executable;
import zure.TargetData;

public class MongoDbService implements Executable {

    @Override
    public List<String> execute(Object connection, TargetData loadedData) throws Exception {
        connection = MongoClients.create();
        MongoDatabase db = ((MongoClient) connection).getDatabase("mydb");
        // TODO Auto-generated method stub
        return null;
    }

}
