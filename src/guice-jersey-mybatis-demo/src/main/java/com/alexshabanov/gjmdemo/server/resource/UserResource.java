package com.alexshabanov.gjmdemo.server.resource;

import com.alexshabanov.gjmdemo.logic.model.User;
import com.alexshabanov.gjmdemo.logic.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
public final class UserResource {
  @Inject UserService userService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  public User getById(@PathParam("id") long id) {
    return userService.getById(id);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/demo/{id}")
  public User getDemoUserById(@PathParam("id") long id) {
    final User user = new User();
    user.setId(id);
    user.setUsername("user-" + id);
    user.setAge(1);
    user.setNote("Note-" + id);
    return user;
  }
}
