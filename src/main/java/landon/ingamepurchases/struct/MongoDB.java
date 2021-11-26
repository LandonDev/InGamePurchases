package landon.ingamepurchases.struct;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
public class MongoDB {
    private MongoDatabase database;
    private MongoClient client;
    private MongoCollection<Document> playerData;
    private MongoCollection<Document> shopData;

    public MongoDB(String stringURI) {
        MongoClientURI uri = new MongoClientURI(stringURI);
        this.client = new MongoClient(uri);
        this.database = this.client.getDatabase("ingame-shop");
        this.playerData = this.database.getCollection("playerData");
        this.shopData = this.database.getCollection("shopData");
    }

    public MongoDB(String host, int port, String username, String password) {
        MongoCredential credential = MongoCredential.createCredential(username, "ingame-shop", password.toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().sslEnabled(false).build();
        this.client = new MongoClient(new ServerAddress(host, port), credential, options);
        this.database = this.client.getDatabase("ingame-shop");
        this.playerData = this.database.getCollection("playerData");
        this.shopData = this.database.getCollection("shopData");
    }
}
