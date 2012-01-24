package com.alexshabanov.rwapp.service.dao;

import com.alexshabanov.rwapp.model.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents operations on the user's account.
 */
public interface UserAccountDao extends JpaRepository<UserAccount, Long> {
    UserAccount findByAlias(String alias);
}
