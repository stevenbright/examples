package com.alexshabanov.guicejerseydemo.server.resource;

import com.alexshabanov.guicejerseydemo.logic.model.User;
import com.alexshabanov.guicejerseydemo.logic.service.UserService;

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
}
