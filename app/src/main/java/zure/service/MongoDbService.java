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
import zure.data.Setting;

public class MongoDbService implements Executable {

    @Override
    public List<String> execute(TargetData loadedData) throws Exception {

        // jdbc:mongodb:Server=MyServer;Port=27017;Database=test;User=test;Password=Password;
        try (MongoClient connection = MongoClients.create(loadedData.url);) {
            MongoDatabase db = connection.getDatabase(loadedData.database);
            MongoCollection<Document> collection = db.getCollection(loadedData.table);

            MongoCursor<Document> mongoCursor = collection.find().iterator();

            List<String> resultList = new ArrayList<>();
            try {
                while (mongoCursor.hasNext()) {
                    StringBuilder keyAndtarget = new StringBuilder(Setting.NOT_YET);
                    // System.out.println(mongoCursor.next().get("name"));
                    Document doc = (Document) mongoCursor.next();
                    int i = 0;
                    for (String keyColumn : loadedData.keyColumns) {
                        if (i++ != 0) {
                            keyAndtarget.append(Setting.SEPARATE);
                        }
                        keyAndtarget.append(String.valueOf(doc.get(keyColumn)));
                    }
                    keyAndtarget.append(Setting.KV_SEPARATE); // この文字列でsplitしてkeyとtargetを判別出来るようにする

                    i = 0;
                    for (String targetColumn : loadedData.targetColumns) {
                        if (i++ != 0) {
                            keyAndtarget.append(Setting.SEPARATE);
                        }
                        keyAndtarget.append(String.valueOf(doc.get(targetColumn)));
                    }
                    // System.out.println("keytarget=" + keyAndtarget.toString());
                    resultList.add(keyAndtarget.toString());
                }
            } catch (Exception e) {
                resultList.clear();
            } finally {
                if (mongoCursor != null)
                    mongoCursor.close();
            }
            return resultList;
        }
    }

}
