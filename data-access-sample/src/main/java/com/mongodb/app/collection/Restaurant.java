package com.mongodb.app.collection;

import com.mongodb.app.resource.MongoDatabaseUtil;
import org.bson.codecs.Encoder;

import java.util.List;

/**
 * Restaurant collection.
 *
 * @author nico.arianto
 */
public class Restaurant extends AbstractCollection {
    private static Encoder<Restaurant> encoder;
    private Address address;
    private String borough;
    private String cuisine;
    private List<Grade> grades;
    private String name;
    private String restaurantId;

    /**
     * Returns an address.
     *
     * @return address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets an address.
     *
     * @param address address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Returns a borough.
     *
     * @return borough
     */
    public String getBorough() {
        return borough;
    }

    /**
     * Sets a borough.
     *
     * @param borough borough
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }

    /**
     * Returns a cuisine.
     *
     * @return cuisine
     */
    public String getCuisine() {
        return cuisine;
    }

    /**
     * Sets a cuisine.
     *
     * @param cuisine cuisine
     */
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    /**
     * Retuns a grades.
     *
     * @return grades
     */
    public List<Grade> getGrades() {
        return grades;
    }

    /**
     * Sets a grades.
     *
     * @param grades grades
     */
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    /**
     * Returns a name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a name.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns restaurant id.
     *
     * @return restaurant id
     */
    public String getRestaurantId() {
        return restaurantId;
    }

    /**
     * Sets restaurant id.
     *
     * @param restaurantId restaurant id
     */
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    /**
     * Returns encoder for restaurant collection.
     *
     * @return encoder
     */
    @Override
    protected Encoder<? extends AbstractCollection> getEncoder() {
        if (encoder == null) {
            encoder = MongoDatabaseUtil.getCodecRegistry().get(Restaurant.class);
        }
        return encoder;
    }
}