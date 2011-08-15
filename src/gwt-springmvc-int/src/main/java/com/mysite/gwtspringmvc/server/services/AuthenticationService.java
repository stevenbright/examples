package com.mysite.gwtspringmvc.server.services;

import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * Server-side only service that provides authentication means for spring security.
 */
public class AuthenticationService implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        if ("alice".equals(username) || "bob".equals(username)) {
            // only one authority is supported by default
            final GrantedAuthority[] authorities = {
                    new GrantedAuthorityImpl("ROLE_USER")
            };

            final String password = "pass";

            return new User(username, password, true, true, true, true, authorities);
        }

        throw new UsernameNotFoundException("User " + username + " is not registered");
    }
}
