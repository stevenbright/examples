package com.truward.web.rest.services;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("simple")
public class SimpleService {
//    @GET
//    @Produces("text/plain")
//    public String getRoot() {
//        return "{ \"message\": \"ok!\" }";
//    }

    @GET
    @Produces("application/json")
    public String getRoot() {
        return "{ \"version\": \"1.0-SNAPSHOT\" }";
    }

    @GET
    @Produces("application/json")
    @Path("/sub")
    public String getName() {
        return "{ \"message\": \"ok!\" }";
    }
}
