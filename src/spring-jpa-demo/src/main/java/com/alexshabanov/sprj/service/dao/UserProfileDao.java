package com.alexshabanov.sprj.service.dao;

import com.alexshabanov.sprj.model.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileDao extends CrudRepository<UserProfile, Long> {
}
