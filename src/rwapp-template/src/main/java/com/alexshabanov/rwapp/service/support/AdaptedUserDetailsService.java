package com.alexshabanov.rwapp.service.support;

import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.service.dao.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Adapted user details service for the spring security authentication and authorization means.
 */
@Service("adaptedUserDetailsService")
public class AdaptedUserDetailsService implements UserDetailsService {
    @Autowired
    private UserProfileDao profileDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserProfile profile = profileDao.findByAccountAlias(username);
        if (profile == null) {
            throw new UsernameNotFoundException("User with alias " + username + " is not found");
        }

        return new User(username, profile.getPassword(), Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER")
        ));
    }
}
