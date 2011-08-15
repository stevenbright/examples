package org.microblog.exposure.server.service;

import org.microblog.exposure.model.Chunk;
import org.microblog.exposure.model.UserAccount;
import org.microblog.exposure.model.UserRole;
import org.microblog.exposure.server.exception.ServiceException;

import java.util.Collection;

/**
 * Represents user service.
 */
public interface UserService extends BaseService {
    /**
     * Gets user account by id.
     * @param userId Unique user account's id.
     * @return User account instance.
     * @throws ServiceException On error.
     */
    UserAccount getUserAccount(long userId) throws ServiceException;

    Chunk<UserAccount> getUserAccounts(int offset, int limit) throws ServiceException;

    Collection<UserRole> getUserRoles(long userId) throws ServiceException;


    long addUserRole(String name) throws ServiceException;

    long registerUser(String login, String password, String email, String avatarUrl) throws ServiceException;

    Long getUserByLoginAndPassword(String login, String password) throws ServiceException;

    void assignUserRole(long userId, long roleId) throws ServiceException;

    void revokeUserRole(long userId, long roleId) throws ServiceException;
}
