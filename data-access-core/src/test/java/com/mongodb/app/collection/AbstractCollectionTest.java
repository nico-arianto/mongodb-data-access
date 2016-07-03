package com.mongodb.app.collection;

import com.mongodb.MongoClient;
import com.mongodb.app.codec.AbstractCollectionCodec;
import mockit.Injectable;
import org.bson.Document;
import org.bson.codecs.Encoder;
import org.bson.codecs.configuration.CodecRegistry;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Abstract collection - unit test.
 *
 * @author nico.arianto
 */
public class AbstractCollectionTest {

    /**
     * Collection - java class.
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

        /**
         * Returns the encoder.
         *
         * @return encoder
         */
        @Override
        protected Encoder<? extends AbstractCollection> getEncoder() {
            return new CollectionTestCodec();
        }
    }

    /**
     * Collection codec - test class.
     *
     * @author nico.arianto
     */
    public static class CollectionTestCodec extends AbstractCollectionCodec<CollectionTest> {

        /**
         * Constructor.
         */
        public CollectionTestCodec() {
            super(MongoClient.getDefaultCodecRegistry(), CollectionTest.class);
        }
    }

    /**
     * Collection unsupported - java class.
     *
     * @author nico.arianto
     */
    public static class CollectionUnsupportedTest extends AbstractCollection {
    }

    /**
     * Test toJson() method.
     *
     * @param stringProp string property
     */
    @Test
    public void testToJson(@Injectable("stringProp") String stringProp) {
        final CollectionTest collection = new CollectionTest();
        collection.setStringProp(stringProp);
        final Document document = new Document();
        document.put("stringProp", stringProp);
        assertEquals(document.toJson(), collection.toJson());
    }

    /**
     * Test toJson() method throw {@link UnsupportedOperationException}.
     */
    @Test(expectedExceptions =  UnsupportedOperationException.class,
            expectedExceptionsMessageRegExp = "Override this method to return codec registry for this collection!")
    public void testToJsonThrowUnsupportedOperationException() {
        new CollectionUnsupportedTest().toJson();
    }

}
