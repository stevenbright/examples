package com.alexshabanov.guiceresteasydemo.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Alexander Shabanov
 */
@Path("hello")
public final class HelloResource  {

  @GET
  @Path("{name}")
  public String hello(@PathParam("name") final String name) {
    return "Hello " + name + " @ "  + System.currentTimeMillis();
  }
}
