package com.mongodb.app.codec;

import com.mongodb.app.collection.AbstractCollection;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.IdGenerator;
import org.bson.codecs.ObjectIdGenerator;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

/**
 * Abstract collection codec.
 *
 * @param <T> collection class
 * @author nico.arianto
 */
public abstract class AbstractCollectionCodec<T extends AbstractCollection> extends AbstractCodec<T> implements CollectibleCodec<T> {
    private final IdGenerator idGenerator = new ObjectIdGenerator();

    /**
     * Constructor.
     *
     * @param registry        registry
     * @param collectionClass collection class
     */
    protected AbstractCollectionCodec(CodecRegistry registry, Class<T> collectionClass) {
        super(registry, collectionClass);
    }

    /**
     * Returns target attribute name.
     * <p>
     * Override this method if the document element name is different with target property name.
     * <p>
     * If the BSON element name is '_id', it will returns 'id'.
     *
     * @param bsonElementName BSON attribute name
     * @return target attribute name
     */
    @Override
    protected String getAttributeName(final String bsonElementName) {
        if (bsonElementName.equalsIgnoreCase(AbstractCollection.COLLECTION_ID)) {
            return AbstractCollection.JAVA_ID;
        }
        return super.getAttributeName(bsonElementName);
    }

    /**
     * Returns BSON element name.
     * <p>
     * Override this method if the BSON element name is different with target attribute name.
     * <p>
     * If the attribute name is 'id', it will returns '_id'.
     *
     * @param attributeName attribute name
     * @return BSON element name
     */
    @Override
    protected String getBSONElementName(final String attributeName) {
        if (attributeName.equalsIgnoreCase(AbstractCollection.JAVA_ID)) {
            return AbstractCollection.COLLECTION_ID;
        }
        return super.getBSONElementName(attributeName);
    }

    /**
     * Generate a value for id property in the given document, if the document does not have one.
     *
     * @param document document
     * @return document with id as hex string value
     */
    @Override
    public T generateIdIfAbsentFromDocument(T document) {
        if (!documentHasId(document)) {
            document.setId((ObjectId) idGenerator.generate());
        }
        return document;
    }

    /**
     * Returns true if the 'id' attribute in the document is not empty.
     *
     * @param document document
     * @return true if 'id' attribute in the document is not empty
     */
    @Override
    public boolean documentHasId(T document) {
        return document.getId() != null;
    }

    /**
     * Returns document object id.
     *
     * @param document document
     * @return document object id
     */
    @Override
    public BsonValue getDocumentId(T document) {
        final ObjectId objectId = document.getId();
        if (objectId == null) {
            throw new IllegalArgumentException("The document id property is empty!");
        }
        BsonDocument idHoldingDocument = new BsonDocument();
        BsonWriter writer = new BsonDocumentWriter(idHoldingDocument);
        writer.writeStartDocument();
        writer.writeName(AbstractCollection.COLLECTION_ID);
        EncoderContext.builder().build().encodeWithChildContext(getCodec(ObjectId.class), writer, objectId);
        writer.writeEndDocument();
        return idHoldingDocument.get(AbstractCollection.COLLECTION_ID);
    }
}