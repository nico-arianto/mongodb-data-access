package com.mongodb.app.resource;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.app.codec.configuration.ApplicationCodecProvider;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.io.IOException;
import java.util.Properties;

/**
 * Mongo database utility.
 * <p>
 * TODO: In the 3-tier application model, {@link MongoClient} or {@link MongoDatabase} should be created with J2EE Connector Architecture (JCA).
 *
 * @author nico.arianto
 */
public class MongoDatabaseUtil {
    private static final String PROPERTIES_FILE = "mongodb.properties";
    private static final String CLIENT_URI = "clientURI";
    private static final String DATABASE = "database";
    private static CodecRegistry registry;

    /**
     * Returns codec registry.
     *
     * @return codec registry
     */
    public static CodecRegistry getCodecRegistry() {
        if (registry == null) {
            registry = CodecRegistries.fromRegistries(CodecRegistries.fromProviders(new ApplicationCodecProvider()),
                    MongoClient.getDefaultCodecRegistry());
        }
        return registry;
    }

    /**
     * Returns mongo database.
     *
     * @return mongo database
     */
    public static MongoDatabase getDatabase() {
        final Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException exception) {
            throw new MongoClientException("Failed to load " + PROPERTIES_FILE + " file.", exception);
        }
        final MongoClientURI clientURI = new MongoClientURI(properties.getProperty(CLIENT_URI),
                new MongoClientOptions.Builder().codecRegistry(getCodecRegistry()));
        final MongoClient client = new MongoClient(clientURI);
        return client.getDatabase(properties.getProperty(DATABASE));
    }
}