package com.alexshabanov.rwapp.service.dao;

import com.alexshabanov.rwapp.model.user.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileDao extends CrudRepository<UserProfile, Long> {
}
