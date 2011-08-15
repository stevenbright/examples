package com.truward.web.jerseysample.rest.exposure;

import com.truward.web.jerseysample.rest.model.Greeting;
import com.truward.web.jerseysample.rest.model.UserProfile;
import com.truward.web.jerseysample.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.util.Date;

/**
 * Sample exposed resource.
 */
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class HelloResource {

    @Context
    private ServletContext servletContext;

    private HelloService helloService;



    private HelloService getHelloService() {
        if (helloService == null) {
            if (servletContext == null) {
                throw new UnsupportedOperationException("Can't fulfill operation without having servlet context");
            }

            final ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            helloService = context.getBean(HelloService.class);
        }

        return helloService;
    }


    @GET
    @Path("greeting")
    public Greeting getGreeting() {
        final Greeting greeting = new Greeting();
        greeting.setCreated(new Date());
        greeting.setUsername("anonimous");
        greeting.setGreeting("hi there");
        return greeting;
    }

    @GET
    @Path("sample")
    public String getSample() {
        return "This is the sample!";
    }

    @POST
    @Path("greeting")
    public Greeting createGreeting(@QueryParam("username") String username) {
        final Greeting greeting = new Greeting();
        greeting.setCreated(new Date());
        greeting.setUsername(username);
        greeting.setGreeting(getHelloService().greetUser("EQ", username));
        return greeting;
    }

    @GET
    @Path("profile")
    public UserProfile getProfile(@QueryParam("created") Date created) {
        final UserProfile userProfile = new UserProfile();
        userProfile.setId(1002);
        userProfile.setDisplayName("Sample Name");
        userProfile.setCreated(created);
        return userProfile;
    }
}
