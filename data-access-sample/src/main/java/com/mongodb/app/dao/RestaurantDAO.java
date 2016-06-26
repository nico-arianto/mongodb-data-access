package com.mongodb.app.dao;

import com.mongodb.app.collection.Restaurant;

/**
 * Restaurant DAO.
 *
 * @author nico.arianto
 */
public class RestaurantDAO extends AbstractDatabaseDAO<Restaurant> {

    /**
     * Constructor.
     */
    public RestaurantDAO() {
        super("restaurants", Restaurant.class);
    }

}