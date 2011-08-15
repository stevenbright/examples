package net.threadtxtest.service.internal.dao;

import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;

/**
 * Represents DAO to work with user profile.
 */
public interface UserDao {
    long addUser(String name) throws DataAccessException;

    Long findUserByName(String name) throws DataAccessException;

    BigDecimal getBalance(long userId) throws DataAccessException;

    void modifyBalance(long userId, BigDecimal amount) throws DataAccessException;
}
