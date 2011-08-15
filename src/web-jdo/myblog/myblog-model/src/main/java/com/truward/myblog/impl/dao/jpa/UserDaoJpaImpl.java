package com.truward.myblog.impl.dao.jpa;

import com.truward.myblog.dao.UserDao;
import com.truward.myblog.dao.exception.ExpectationFailedException;
import com.truward.myblog.model.user.Profile;
import com.truward.myblog.model.user.Role;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import java.util.List;


public class UserDaoJpaImpl extends JpaDaoSupport implements UserDao {
    public void save(Profile profile) {
        getJpaTemplate().persist(profile);
    }

    public void save(Role role) {
        getJpaTemplate().persist(role);
    }

    public void update(Profile profile) {
        getJpaTemplate().merge(profile);
    }

    public void remove(long profileId) {
        final Profile foundItem = getJpaTemplate().find(Profile.class, profileId);
        getJpaTemplate().remove(foundItem);
    }

    public Profile findProfileByLogin(String login) {
        final List profiles = getJpaTemplate().find("select P from Profile P where P.login = ?1", login);

        if (profiles.size() == 0) {
            return null;
        } else if (profiles.size() > 1) {
            throw new ExpectationFailedException("There are too much profiles that corresponds to the login given");
        }

        return (Profile) profiles.get(0);
    }

    public Role findRoleByName(String name) {
        final List roles = getJpaTemplate().find("select R from Role R where R.name = ?1", name);

        if (roles.size() == 0) {
            return null;
        } else if (roles.size() > 1) {
            throw new ExpectationFailedException("There are too much roles that corresponds to the name given");
        }

        return (Role) roles.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Profile> getProfiles() {
        return getJpaTemplate().find("select P from Profile P");
    }
}
