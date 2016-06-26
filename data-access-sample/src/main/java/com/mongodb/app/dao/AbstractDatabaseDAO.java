package com.mongodb.app.dao;

import com.mongodb.app.collection.AbstractCollection;
import com.mongodb.app.resource.MongoDatabaseUtil;
import com.mongodb.client.MongoDatabase;

/**
 * Abstract database DAO.
 *
 * @author nico.arianto
 */
public abstract class AbstractDatabaseDAO<T extends AbstractCollection> extends AbstractDAO<T> {

    /**
     * Constructor.
     *
     * @param collectionName  collection name
     * @param collectionClass collection class
     */
    protected AbstractDatabaseDAO(String collectionName, Class<T> collectionClass) {
        super(collectionName, collectionClass);
    }

    /**
     * Returns the database object.
     *
     * @return database
     */
    @Override
    protected MongoDatabase getDatabase() {
        return MongoDatabaseUtil.getDatabase();
    }
}