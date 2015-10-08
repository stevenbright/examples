package com.alexshabanov.guicejerseydemo.server.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sample")
public final class SampleResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("{who}")
  public String getGreetingFor(@PathParam("who") String name) {
    return "Greetings, " + name + "!";
  }
}
