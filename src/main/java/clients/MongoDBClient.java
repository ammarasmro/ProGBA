package clients;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.*;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.model.Sorts;
import com.mongodb.Block;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.FullDocument;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;
import java.util.Arrays;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;

public class MongoDBClient {

    String user = "user1"; // the user name
    String database = "admin"; // the name of the database in which the user is defined
    char[] password = "pwd1".toCharArray(); // the password as a character array
    // ...

    MongoDBClient(){
//        MongoCredential credential = MongoCredential.createCredential(user, database, password);
//
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .credential(credential)
//                .applyToSslSettings(builder -> builder.enabled(true))
//                .applyToClusterSettings(builder ->
//                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
//                .build();

//        MongoClient mongoClient = MongoClients.create();
        MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));

        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("restaurants");
//        ValidationOptions collOptions = new ValidationOptions().validator(
//                Filters.or(Filters.exists("email"), Filters.exists("phone")));
//        database.createCollection("contacts",
//                new CreateCollectionOptions().validationOptions(collOptions));
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };

        Block<ChangeStreamDocument<Document>> printBlockWatch = new Block<ChangeStreamDocument<Document>>() {
            @Override
            public void apply(final ChangeStreamDocument<Document> changeStreamDocument) {
                System.out.println("changeStreamDoc" + changeStreamDocument);
            }
        };

        collection.find().forEach(printBlock);
        collection.find(eq("name", "456 Cookies Shop"))
                .forEach(printBlock);
        collection.find(
                new Document("stars", new Document("$gte", 2)
                        .append("$lt", 5))
                        .append("categories", "Bakery")).forEach(printBlock);
        collection.watch().forEach(printBlockWatch);

    }

    public static void main(String[] args) {
        MongoDBClient client = new MongoDBClient();
    }

}
