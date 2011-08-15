package com.truward.blogboard.dao;


import com.truward.blogboard.domain.*;

import java.util.List;

public interface BlogboardDao {
    Integer getUserId(String login, String password);

    UserProfile getProfile(int userId);

    List<UserAccount> getAccounts(int userId, boolean onlyFriends, String usernameKeyword);

    SipAccount getSipAccount(int id);

    List<ProfileData> getProfileDataList();

    List<SocialProfileData> getProfileDataList(int profileId);

    void saveProfileData(ProfileData profileData);
}
