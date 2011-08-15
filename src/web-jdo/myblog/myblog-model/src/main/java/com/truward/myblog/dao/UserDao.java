package com.truward.myblog.dao;

import com.truward.myblog.model.user.Profile;
import com.truward.myblog.model.user.Role;

import java.util.List;

/**
 * Represents operations on Users.
 */
public interface UserDao {
    void save(Profile profile);

    void save(Role role);

    void update(Profile profile);

    void remove(long profileId);

    Profile findProfileByLogin(String login);

    Role findRoleByName(String name);

    List<Profile> getProfiles();
}
