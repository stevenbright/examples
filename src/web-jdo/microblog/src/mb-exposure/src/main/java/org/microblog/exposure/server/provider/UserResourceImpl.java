package org.microblog.exposure.server.provider;

import org.microblog.exposure.model.UserAccount;
import org.microblog.exposure.model.UserRole;
import org.microblog.exposure.server.AbstractResource;
import org.microblog.exposure.server.service.UserService;
import org.microblog.exposure.shared.model.NewUser;
import org.microblog.exposure.shared.provider.UserResource;

import javax.ws.rs.Path;
import java.util.Collection;


/**
 * Implements user resource.
 */
@Path("/user")
public final class UserResourceImpl extends AbstractResource implements UserResource {
    /**
     * {@inheritDoc}
     */
    public UserAccount getUserAccount(long userId) {
        return getService(UserService.class).getUserAccount(userId);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<UserRole> getUserRoles(long userId) {
        return getService(UserService.class).getUserRoles(userId);
    }

    /**
     * {@inheritDoc}
     */
    public long addUserRole(String name) {
        return getService(UserService.class).addUserRole(name);
    }

    /**
     * {@inheritDoc}
     */
    public long registerUser(NewUser newUser) {
        return getService(UserService.class).registerUser(newUser.getLogin(), newUser.getPassword(),
                newUser.getEmail(), newUser.getAvatarUrl());
    }

    /**
     * {@inheritDoc}
     */
    public void assignUserRole(long userId, long roleId) {
        getService(UserService.class).assignUserRole(userId, roleId);
    }

    /**
     * {@inheritDoc}
     */
    public void revokeUserRole(long userId, long roleId) {
        getService(UserService.class).revokeUserRole(userId, roleId);
    }
}
