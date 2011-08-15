package com.truward.web.rest.services;


import com.truward.web.model.AuthParameters;
import com.truward.web.model.Movie;
import com.truward.web.model.MovieList;

import javax.ws.rs.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.GregorianCalendar;

@Path("complex")
@Produces("application/json")
public class ComplexService {

    @GET
    public String getRoot() {
        return "{ \"message\": \"produces complex operation\" }";
    }

    @GET
    @Path("/binop")
    public String getBinaryOpResult(@QueryParam("a") Integer a,
                              @QueryParam("b") Integer b,
                              @HeaderParam("Binary-Operation") String binaryOperation) {
        if (binaryOperation == null) {
            binaryOperation = "+";
        }

        if (a == null || b == null) {
            return "{ \"result\": null }";
        }

        final int c;
        if ("+".equals(binaryOperation)) {
            c = a + b;
        } else if ("-".equals(binaryOperation)) {
            c = a - b;
        } else if ("*".equals(binaryOperation)) {
            c = a * b;
        } else if ("/".equals(binaryOperation)) {
            c = a / b;
        } else {
            return "{ \"result\": null, message: \"unknown operation\" }";
        }

        return "{ \"result\": " + c + " }";
    }

    @POST
    @Path("/auth")
    public AuthParameters doLogin(@QueryParam("temporaryToken") String temporaryToken) {
        return new AuthParameters("permanent-" + temporaryToken, 123L);
    }

    @POST
    @Path("/movie")
    public Movie createMovie() {
        final Movie movie = new Movie();
        movie.setTitle("The Door");
        movie.setTags(new String[] { "action", "fantasy" });
        movie.setProducers(new String[] { "Chris Tailor", "James Cameron", "Evan Rightman" });
        movie.setDurationInSeconds(5452);
        movie.setRating(9.2);
        movie.setBudget(new BigDecimal(900987.21));
        movie.setCreated(new GregorianCalendar(1997, 4, 28, 12, 42, 37).getTime());
        return movie;
    }

    @POST
    @Path("/movies")
    public MovieList createMovies() {
        final Movie m1 = new Movie();
        m1.setTitle("The Door");
        m1.setTags(new String[] { "action", "fantasy" });
        m1.setProducers(new String[] { "Chris Tailor", "James Cameron", "Evan Rightman" });
        m1.setDurationInSeconds(5452);
        m1.setRating(9.2);
        m1.setBudget(new BigDecimal(900987.21));

        final Movie m2 = new Movie();
        m2.setTitle("The Big Bang Theory");
        m2.setTags(new String[]{"comedy"});
        m2.setDurationInSeconds(3687);
        m2.setRating(8.7);

        return new MovieList(Arrays.asList(m1, m2));
    }
}
