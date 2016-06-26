package com.mongodb.app.codec;

import com.mongodb.app.collection.Grade;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Grade codec.
 *
 * @author nico.arianto
 */
public class GradeCodec extends AbstractCodec<Grade> {

    /**
     * Constructor.
     *
     * @param registry registry
     */
    public GradeCodec(CodecRegistry registry) {
        super(registry, Grade.class);
    }
}