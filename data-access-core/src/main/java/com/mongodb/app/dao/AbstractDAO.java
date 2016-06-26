package com.mongodb.app.dao;

import com.mongodb.app.collection.AbstractCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.eq;

/**
 * Abstract DAO.
 *
 * @param <T> collection class
 * @author nico.arianto
 */
public abstract class AbstractDAO<T extends AbstractCollection> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAO.class);
    protected final MongoCollection collection;

    /**
     * Constructor.
     *
     * @param collectionName  collection name
     * @param collectionClass collection class
     */
    protected AbstractDAO(String collectionName, Class<T> collectionClass) {
        collection = getDatabase().getCollection(collectionName, collectionClass);
    }

    /**
     * Returns the database object.
     *
     * @return database
     */
    protected abstract MongoDatabase getDatabase();

    /**
     * Create a document.
     *
     * @param document document
     */
    public void create(T document) {
        collection.insertOne(document);
    }

    /**
     * Read a document.
     *
     * @param id id
     * @return document
     */
    public T read(ObjectId id) {
        return (T) collection.find(eq(AbstractCollection.COLLECTION_ID, id)).first();
    }

    /**
     * Replace a document.
     *
     * @param id       id
     * @param document document
     */
    public void update(ObjectId id, T document) {
        final UpdateResult result = collection.replaceOne(eq(AbstractCollection.COLLECTION_ID, id), document);
        LOGGER.debug("Replace one: {}", result);
    }

    /**
     * Delete a document.
     *
     * @param id id
     */
    public void delete(ObjectId id) {
        final DeleteResult result = collection.deleteOne(eq(AbstractCollection.COLLECTION_ID, id));
        LOGGER.debug("Delete one: {}", result);
    }
}