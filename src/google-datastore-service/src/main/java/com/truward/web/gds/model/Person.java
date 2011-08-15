package com.truward.web.gds.model;

/**
 * Person model facade.
 */
public interface Person {
    long getId();

    String getDisplayName();

    double getBalance();

    int getAge();

    String getStatus();
}
