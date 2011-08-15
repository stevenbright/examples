package com.truward.langexp.typedisp.domain;


public enum ProfilePropertyType {
    // string property
    EMAIL("EMAIL"),
    DISPLAY_NAME("EMAIL"),
    DISPLAY_URL("EMAIL"),

    // int property
    AGE("AGE"),

    // double property
    HEIGHT("HEIGHT"),
    WEIGHT("WEIGHT"),

    // boolean property
    SUBSCRIBED("SUBSCRIBED");

    private String propertyName;

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String toString() {
        return "ProfilePropertyType:" + propertyName;
    }

    ProfilePropertyType(String propertyName) {
        this.propertyName = propertyName;
    }
}
