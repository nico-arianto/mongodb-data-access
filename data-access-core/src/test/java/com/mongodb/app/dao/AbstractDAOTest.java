package com.mongodb.app.dao;

import com.mongodb.app.collection.AbstractCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Abstract DAO - unit test.
 *
 * @author nico.arianto
 */
public class AbstractDAOTest {
    @Mocked
    private MongoDatabase database;
    @Mocked
    private MongoCollection<AbstractCollection> collection;
    @Mocked
    private AbstractCollection document;
    @Mocked
    private Filters filters;
    private AbstractDAO<AbstractCollection> dao;

    /**
     * Test constructor.
     */
    @Test
    public void testConstructor(@Injectable("collectionName") String collectionName) {
        new Expectations() {
            {
                database.getCollection(collectionName, AbstractCollection.class);
                result = collection;
            }
        };
        dao = new AbstractDAO<AbstractCollection>(collectionName, AbstractCollection.class) {

            @Override
            protected MongoDatabase getDatabase() {
                return database;
            }

        };
    }

    /**
     * Test create(T) method.
     */
    @Test(dependsOnMethods = "testConstructor")
    public void testCreate() {
        dao.create(document);
        new Verifications() {
            {
                collection.insertOne(document);
                times = 1;
            }
        };
    }

    /**
     * Test read(T) method.
     */
    @Test(dependsOnMethods = "testConstructor")
    public void testRead(@Mocked FindIterable iterable) {
        final ObjectId objectId = new ObjectId();
        new Expectations() {
            {
                collection.find(withInstanceOf(Bson.class));
                result = iterable;
                iterable.first();
                result = document;
            }
        };
        final AbstractCollection actualDocument = dao.read(objectId);
        assertNotNull(actualDocument);
        assertEquals(document, actualDocument);
        new Verifications() {
            {
                Filters.eq(AbstractCollection.COLLECTION_ID, objectId);
                times = 1;
            }
        };
    }

}
