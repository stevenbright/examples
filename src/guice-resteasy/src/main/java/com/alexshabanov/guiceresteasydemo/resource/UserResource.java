package com.alexshabanov.guiceresteasydemo.resource;

import com.alexshabanov.guiceresteasydemo.model.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
public final class UserResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  public User getById(@PathParam("id") long id) {
    final User user = new User();
    user.setId(id);
    user.setAge(19);
    user.setUsername("alice");
    user.setNote("SysAdm");
    return user;
  }
}
