package com.truward.myblog.model.user;

import com.truward.myblog.model.PersistentBean;

import java.util.List;

/**
 * User's role.
 */
public class Role implements PersistentBean {
    private int id;

    private String name;

    private List<Profile> profiles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
