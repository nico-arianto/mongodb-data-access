package com.mongodb.app.codec.configuration;

import com.mongodb.app.codec.AddressCodec;
import com.mongodb.app.codec.GradeCodec;
import com.mongodb.app.codec.RestaurantCodec;
import com.mongodb.app.collection.Address;
import com.mongodb.app.collection.Grade;
import com.mongodb.app.collection.Restaurant;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Application codec provider.
 *
 * @author nico.arianto
 */
public class ApplicationCodecProvider implements CodecProvider {

    /**
     * Returns codec.
     *
     * @param clazz collection class
     * @param registry codec registry
     * @param <T> collection class
     * @return codec
     */
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == Restaurant.class) {
            return (Codec<T>) new RestaurantCodec(registry);
        } else if (clazz == Grade.class) {
            return (Codec<T>) new GradeCodec(registry);
        } else if (clazz == Address.class) {
            return (Codec<T>) new AddressCodec(registry);
        }
        return null;
    }
}