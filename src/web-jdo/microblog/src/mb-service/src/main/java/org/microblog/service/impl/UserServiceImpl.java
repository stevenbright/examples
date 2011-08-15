package org.microblog.service.impl;

import org.microblog.exposure.model.Chunk;
import org.microblog.exposure.model.UserAccount;
import org.microblog.exposure.model.UserRole;
import org.microblog.exposure.server.exception.ServiceException;
import org.microblog.exposure.server.service.UserService;
import org.microblog.service.dao.UserDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Represents service implementation.
 */
@Transactional
public final class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    private Long userRoleId;

    private static final String ROLE_USER = "ROLE_USER";



    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public UserAccount getUserAccount(long userId) throws ServiceException {
        return userDao.getUserAccount(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Chunk<UserAccount> getUserAccounts(int offset, int limit) throws ServiceException {
        final Chunk<UserAccount> result = new Chunk<UserAccount>();
        result.setCount(userDao.getUserAccountCount());
        result.setItems(userDao.getUserAccounts(offset, limit));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Collection<UserRole> getUserRoles(long userId) throws ServiceException {
        return userDao.getUserRoles(userId);
    }

    /**
     * {@inheritDoc}
     */
    public long addUserRole(String name) throws ServiceException {
        return userDao.addNewRole(name);
    }

    /**
     * {@inheritDoc}
     */
    public long registerUser(String login, String password, String email, String avatarUrl) throws ServiceException {
        final long userId = userDao.addUser(login, password, email, avatarUrl);

        // add ROLE_USER to the user
        if (userRoleId == null) {
            userRoleId = userDao.getRoleId(ROLE_USER);
        }

        assert userRoleId != null;

        userDao.addRoleToUser(userId, userRoleId);

        return userId;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Long getUserByLoginAndPassword(String login, String password) throws ServiceException {
        return userDao.getUserByLoginAndPassword(login, password);
    }

    /**
     * {@inheritDoc}
     */
    public void assignUserRole(long userId, long roleId) throws ServiceException {
        userDao.addRoleToUser(userId, roleId);
    }

    /**
     * {@inheritDoc}
     */
    public void revokeUserRole(long userId, long roleId) throws ServiceException {
        userDao.deleteUserRole(userId, roleId);
    }
}
