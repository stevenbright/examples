package org.microblog.exposure.shared.provider;

import org.microblog.exposure.model.UserAccount;
import org.microblog.exposure.model.UserRole;
import org.microblog.exposure.shared.model.NewUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Represents resource used for user retrieval.
 */
@Produces(MediaType.APPLICATION_JSON)
public interface UserResource {
    /**
     * Gets user account.
     * @param userId Unique account ID.
     * @return User account.
     */
    @GET
    @Path("/account")
    UserAccount getUserAccount(@QueryParam("userId") long userId);

    @GET
    @Path("/assigned/roles")
    Collection<UserRole> getUserRoles(@QueryParam("userId") long userId);

    @POST
    @Path("/role")
    long addUserRole(@QueryParam("name") String name);

    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    long registerUser(NewUser newUser);

    @POST
    @Path("/assigned/role")
    void assignUserRole(@QueryParam("userId") long userId, @QueryParam("roleId") long roleId);

    @DELETE
    @Path("/assigned/role")
    void revokeUserRole(@QueryParam("userId") long userId, @QueryParam("roleId") long roleId);
}
