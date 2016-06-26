package com.mongodb.app.codec;

import com.mongodb.app.collection.Restaurant;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Restaurant codec.
 *
 * @author nico.arianto
 */
public class RestaurantCodec extends AbstractCollectionCodec<Restaurant> {
    private static final String JAVA_RESTAURANT_ID = "restaurantId";
    private static final String COLLECTION_RESTAURANT_ID = "restaurant_id";

    /**
     * Constructor.
     *
     * @param registry registry
     */
    public RestaurantCodec(CodecRegistry registry) {
        super(registry, Restaurant.class);
    }

    /**
     * Returns element name as 'restaurant_id' if attribute name is 'restaurantId'.
     *
     * @param attributeName attribute name
     * @return element name
     */
    @Override
    protected String getBSONElementName(String attributeName) {
        if (attributeName.equalsIgnoreCase(JAVA_RESTAURANT_ID)) {
            return COLLECTION_RESTAURANT_ID;
        }
        return super.getBSONElementName(attributeName);
    }

    /**
     * Returns attribute name as 'restaurantId' if element name is 'restaurant_id'.
     *
     * @param bsonElementName BSON attribute name
     * @return attribute name
     */
    @Override
    protected String getAttributeName(String bsonElementName) {
        if (bsonElementName.equalsIgnoreCase(COLLECTION_RESTAURANT_ID)) {
            return JAVA_RESTAURANT_ID;
        }
        return super.getAttributeName(bsonElementName);
    }
}