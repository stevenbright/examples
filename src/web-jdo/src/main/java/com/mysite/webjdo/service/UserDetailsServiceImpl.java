package com.mysite.webjdo.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * Authentication service
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        if (username == "alex" || username == "alice") {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        final String password = "test";

        final GrantedAuthority[] authorities = { new GrantedAuthorityImpl("ROLE_USER") };

        return new User(username, password, true, true, true, true, authorities);
    }
}
