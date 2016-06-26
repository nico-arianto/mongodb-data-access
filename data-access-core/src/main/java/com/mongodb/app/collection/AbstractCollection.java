package com.mongodb.app.collection;

import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.io.StringWriter;

/**
 * Abstract collection.
 *
 * @author nico.arianto
 */
public abstract class AbstractCollection {
    public static final String COLLECTION_ID = "_id";
    public static final String JAVA_ID = "id";
    private ObjectId id;

    /**
     * Returns object id.
     *
     * @return object id
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Sets object id.
     *
     * @param id object id
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Returns encoder for this collection.
     * <p>
     * Note: Override this method to return the encoder for this collection
     *
     * @return codec registry
     */
    protected Encoder<? extends AbstractCollection> getEncoder() {
        throw new UnsupportedOperationException("Override this method to return codec registry for this collection!");
    }

    /**
     * Returns a JSON representation of this document.
     *
     * @return JSON representation
     * @throws UnsupportedOperationException if getCodecRegistry() method haven't been overrided to return the codec registry for this collection.
     */
    @SuppressWarnings("unchecked")
    public String toJson() {
        final Encoder encoder = getEncoder();
        final JsonWriter writer = new JsonWriter(new StringWriter(), new JsonWriterSettings());
        encoder.encode(writer, this, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        return writer.getWriter().toString();
    }
}