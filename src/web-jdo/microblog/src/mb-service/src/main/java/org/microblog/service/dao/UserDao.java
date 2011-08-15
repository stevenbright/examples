package org.microblog.service.dao;

import org.microblog.exposure.model.UserAccount;
import org.microblog.exposure.model.UserRole;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

/**
 * Represents user DAO.
 */
public interface UserDao {
    UserAccount getUserAccount(long userId) throws DataAccessException;

    int getUserAccountCount() throws DataAccessException;

    Collection<UserAccount> getUserAccounts(int offset, int limit) throws DataAccessException;

    Collection<UserRole> getUserRoles(long userId) throws DataAccessException;

    Long getRoleId(String name) throws DataAccessException;

    long addNewRole(String name) throws DataAccessException;

    long addUser(String login, String password, String email, String avatarUrl) throws DataAccessException;

    Long getUserByLoginAndPassword(String login, String password) throws DataAccessException;

    void addRoleToUser(long userId, long roleId) throws DataAccessException;

    void deleteUserRole(long userId, long roleId) throws DataAccessException;
}
