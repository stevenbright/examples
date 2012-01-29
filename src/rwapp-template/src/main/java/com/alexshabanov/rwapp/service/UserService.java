package com.alexshabanov.rwapp.service;

import com.alexshabanov.rwapp.model.user.UserProfile;

/**
 * Encapsulates all the user-related methods under the convenient service facade.
 */
public interface UserService {
    UserProfile findProfileByAlias(String accountAlias);
    
    UserProfile createProfile(String password, String nickname, String... roleCodes);
    
    void saveRoles(String... roleCodes);
}
