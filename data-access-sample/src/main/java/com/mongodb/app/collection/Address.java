package com.mongodb.app.collection;

/**
 * Address collection.
 *
 * @author nico.arianto
 */
public class Address {
    private String street;
    private String zipcode;
    private String building;
    private Double[] coord;

    /**
     * Returns a street.
     *
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets a street.
     *
     * @param street street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Returns a zip code.
     *
     * @return zip code
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets a zip code.
     *
     * @param zipcode zip code
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Returns a building.
     *
     * @return building
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Sets a building.
     *
     * @param building building
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    /**
     * Returns a coordinate.
     *
     * @return coord
     */
    public Double[] getCoord() {
        return coord;
    }

    /**
     * Sets a cordinate.
     *
     * @param coord coordinate
     */
    public void setCoord(Double[] coord) {
        this.coord = coord;
    }
}