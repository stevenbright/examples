package com.alexshabanov.rwapp.service.dao;

import com.alexshabanov.rwapp.model.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represents operations on the user's account.
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface UserAccountDao extends JpaRepository<UserAccount, Long> {
    UserAccount findByAlias(String alias);
}
