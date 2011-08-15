package com.truward.blogboard.impl.dao;

import com.truward.blogboard.dao.BlogboardDao;
import com.truward.blogboard.domain.*;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogboardDaoImpl extends JpaDaoSupport implements BlogboardDao {
    @Override
    public Integer getUserId(String login, String password) {
        return null;
    }

    @Override
    public UserProfile getProfile(int userId) {
        return getJpaTemplate().find(UserProfile.class, userId);
    }

    @Override
    public List<UserAccount> getAccounts(int userId, boolean onlyFriends, String usernameKeyword) {
        return new ArrayList<UserAccount>();
    }

    @Override
    public SipAccount getSipAccount(int id) {
        return getJpaTemplate().find(SipAccount.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProfileData> getProfileDataList() {
        return getJpaTemplate().find("select profile from ProfileData profile");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SocialProfileData> getProfileDataList(int profileId) {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", profileId);

        List result = getJpaTemplate().findByNamedParams(
                "select new com.truward.blogboard.domain.SocialProfileData(" +
                        "p.username, " +
                        "case when exists (select q.id from ProfileData q where p member of q.friends) " +
                        "then true else false end" +
                        ") " +
                        "from ProfileData p where p.id<>:id", parameters);

        return (List<SocialProfileData>) result;
    }

    @Override
    public void saveProfileData(ProfileData profileData) {
        getJpaTemplate().persist(profileData);
    }
}
