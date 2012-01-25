package com.alexshabanov.rwapp.service.dao;

import com.alexshabanov.rwapp.model.user.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileDao extends CrudRepository<UserProfile, Long> {
    @Query("SELECT u FROM UserProfile u JOIN u.accounts acc WHERE acc.alias = ?1")
    UserProfile findByAccountAlias(String accountAlias);
}
