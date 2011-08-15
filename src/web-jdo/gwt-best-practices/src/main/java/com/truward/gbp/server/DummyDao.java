package com.truward.gbp.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Emulates data access object (part of server's DB access layer)
 */
public final class DummyDao {

    private final class Profile {
        int age;

        Profile(int age) {
            this.age = age;
        }
    }

    private Map<String, Profile> profiles = new HashMap<String, Profile>();

    private final static String DEFAULT_PASSWORD = "test";



    public DummyDao() {
        profiles.put("alice", new Profile(18));
        profiles.put("bob", new Profile(25));
    }

    public final boolean isCredentialsValid(String username, String password) {
        return DEFAULT_PASSWORD.equals(password) && profiles.containsKey(username);
    }
}
