package com.mongodb.app.codec;

import com.mongodb.MongoClient;
import com.mongodb.app.collection.AbstractCollection;
import mockit.Injectable;
import org.bson.BsonObjectId;
import org.bson.BsonValue;
import org.bson.types.ObjectId;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Abstract collection codec - unit test.
 *
 * @author nico.arianto
 */
public class AbstractCollectionCodecTest {

    /**
     * Collection - test class.
     *
     * @author nico.arianto
     */
    public static class CollectionTest extends AbstractCollection {
        private String stringProp;

        /**
         * Returns string property.
         *
         * @return string property
         */
        public String getStringProp() {
            return stringProp;
        }

        /**
         * Sets string property.
         *
         * @param stringProp string property
         */
        public void setStringProp(String stringProp) {
            this.stringProp = stringProp;
        }
    }

    final AbstractCollectionCodec<CollectionTest> collectionCodec =
            new AbstractCollectionCodec<CollectionTest>(MongoClient.getDefaultCodecRegistry(), CollectionTest.class) {
            };

    /**
     * Test getAttributeName(String) method.
     *
     * @param stringProp string property
     */
    @Test
    public void testGetAttributeName(@Injectable("stringProp") String stringProp) {
        final String actualAttributeName = collectionCodec.getAttributeName(stringProp);
        assertNotNull(actualAttributeName);
        assertEquals(stringProp, actualAttributeName);
    }

    /**
     * Test getAttributeName(String) method with a collection id.
     */
    @Test
    public void testGetAttributeNameWithCollectionId() {
        final String actualAttributeName = collectionCodec.getAttributeName(AbstractCollection.COLLECTION_ID);
        assertNotNull(actualAttributeName);
        assertEquals(AbstractCollection.JAVA_ID, actualAttributeName);
    }

    /**
     * Test getBSONElementName(String) method.
     *
     * @param stringProp string property
     */
    @Test
    public void testGetBSONElementName(@Injectable("string_prop") String stringProp) {
        final String actualElementName = collectionCodec.getBSONElementName(stringProp);
        assertNotNull(actualElementName);
        assertEquals(stringProp, actualElementName);
    }

    /**
     * Test getBSONElementName(String) method with a java id.
     */
    @Test
    public void testGetBSONElementNameWithJavaId() {
        final String actualElementName = collectionCodec.getBSONElementName(AbstractCollection.JAVA_ID);
        assertNotNull(actualElementName);
        assertEquals(AbstractCollection.COLLECTION_ID, actualElementName);
    }

    /**
     * Test generateIfAbsentFromDocument(T) method.
     */
    @Test
    public void testGenerateIfAbsentFromDocument() {
        final CollectionTest collection = new CollectionTest();
        collectionCodec.generateIdIfAbsentFromDocument(collection);
        assertNotNull(collection.getId());
    }

    /**
     * Test generateIfAbsentFromDocument(T) method with a document has id.
     */
    @Test
    public void testGenerateIfAbsentFromDocumentWithDocumentHasId() {
        final ObjectId objectId = new ObjectId();
        final CollectionTest collection = new CollectionTest();
        collection.setId(objectId);
        collectionCodec.generateIdIfAbsentFromDocument(collection);
        assertEquals(objectId, collection.getId());
    }

    /**
     * Test getDocumentId(T) method.
     */
    @Test
    public void testGetDocumentId() {
        final ObjectId objectId = new ObjectId();
        final CollectionTest collection = new CollectionTest();
        collection.setId(objectId);
        final BsonValue actualValue = collectionCodec.getDocumentId(collection);
        assertNotNull(actualValue);
        assertTrue(BsonObjectId.class.isInstance(actualValue));
        assertEquals(objectId, ((BsonObjectId) actualValue).getValue());
    }

    /**
     * Test getDocumentId(T) method with a document that do not have id.
     */
    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "The document id property is empty!")
    public void testGetDocumentIdWithNullId() {
        collectionCodec.getDocumentId(new CollectionTest());
    }

}
