package com.truward.web.model;

import java.util.Collection;

/**
 * The collection of movies.
 */
public class MovieList {
    private final Collection<Movie> movies;

    public Collection<Movie> getMovies() {
        return movies;
    }

    public MovieList(Collection<Movie> movies) {
        this.movies = movies;
    }
}
