package com.alexshabanov.guiceresteasydemo.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Alexander Shabanov
 */
@Path("hello")
public class HelloResource
{
  @GET
  @Path("{name}")
  public String hello(@PathParam("name") final String name) {
    return "Hello " + name;
  }
}
