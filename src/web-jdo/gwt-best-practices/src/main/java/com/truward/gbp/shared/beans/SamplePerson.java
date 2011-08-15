package com.truward.gbp.shared.beans;

/**
 * Sample bean that stores some person's info what is needed on the client side 
 */
public class SamplePerson {
    private String name;
    private int rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
