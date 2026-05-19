import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoManager {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private static final String DATABASE_NAME = "db_simandi";

    public static MongoDatabase getDatabase() {

        if (database == null) {

            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(
                            PojoCodecProvider.builder().automatic(true).build()
                    )
            );

            mongoClient = MongoClients.create("mongodb://localhost:27017");

            database = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .withCodecRegistry(pojoCodecRegistry);
        }

        return database;
    }
}