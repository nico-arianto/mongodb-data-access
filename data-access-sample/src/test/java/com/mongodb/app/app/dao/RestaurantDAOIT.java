package com.mongodb.app.app.dao;

import com.mongodb.app.collection.Address;
import com.mongodb.app.collection.Grade;
import com.mongodb.app.collection.Restaurant;
import com.mongodb.app.dao.RestaurantDAO;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.testng.AssertJUnit.*;

/**
 * Restaurant DAO - integration test.
 *
 * @author nico.arianto
 */
public class RestaurantDAOIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantDAOIT.class);
    private RestaurantDAO restaurantDAO;
    private Restaurant restaurant;

    /**
     * Initialize restaurant DAO instance.
     *
     * @throws ParseException
     */
    @BeforeClass
    public void init() throws ParseException {
        restaurantDAO = new RestaurantDAO();
        restaurant = new Restaurant();
        final Address address = new Address();
        restaurant.setAddress(address);
        address.setStreet("2 Avenue");
        address.setZipcode("10075");
        address.setBuilding("1480");
        address.setCoord(new Double[]{-73.9557413, 40.7720266});
        restaurant.setBorough("Manhattan");
        restaurant.setCuisine("Italian");
        final List<Grade> grades = new ArrayList<>(2);
        restaurant.setGrades(grades);
        Grade grade = new Grade();
        grades.add(grade);
        grade.setDate(Calendar.getInstance().getTime());
        grade.setGrade("A");
        grade.setScore(11);
        grade = new Grade();
        grades.add(grade);
        grade.setDate(Calendar.getInstance().getTime());
        grade.setGrade("B");
        grade.setScore(17);
        restaurant.setName("Vella");
        restaurant.setRestaurantId("41704620");
    }

    /**
     * Test create(Document) method.
     */
    @Test
    public void testCreate() {
        restaurantDAO.create(restaurant);
        final Restaurant actualRestaurant = restaurantDAO.read(restaurant.getId());
        assertNotNull(actualRestaurant);
        final String actualJson = actualRestaurant.toJson();
        LOGGER.info("Restaurant created = {}", actualJson);
        assertEquals(restaurant.toJson(), actualJson);
    }

    /**
     * Test update(String, Document) method.
     */
    @Test(dependsOnMethods = "testCreate")
    public void testUpdate() {
        final ObjectId id = restaurant.getId();
        restaurant.setCuisine("American (New)");
        restaurantDAO.update(id, restaurant);
        final Restaurant actualRestaurant = restaurantDAO.read(id);
        assertNotNull(actualRestaurant);
        final String actualJson = actualRestaurant.toJson();
        LOGGER.info("Restaurant updated = {}", actualJson);
        assertEquals(restaurant.toJson(), actualRestaurant.toJson());
    }

    /**
     * Test delete(String) method.
     */
    @Test(dependsOnMethods = "testUpdate")
    public void testDelete() {
        final ObjectId id = restaurant.getId();
        restaurantDAO.delete(id);
        LOGGER.info("Restaurant deleted");
        assertNull(restaurantDAO.read(id));
    }
}