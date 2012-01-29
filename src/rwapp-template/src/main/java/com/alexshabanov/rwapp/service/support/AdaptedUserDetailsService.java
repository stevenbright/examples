package com.alexshabanov.rwapp.service.support;

import com.alexshabanov.rwapp.model.security.UserWithId;
import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.model.user.UserRole;
import com.alexshabanov.rwapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adapted user details service for the spring security authentication and authorization means.
 */
@Service("adaptedUserDetailsService")
@Transactional
public class AdaptedUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserProfile profile = userService.findProfileByAlias(username);
        if (profile == null) {
            throw new UsernameNotFoundException("User with alias " + username + " is not found");
        }

        final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (final UserRole role : profile.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        }

        return new UserWithId(profile.getId(), username, profile.getPassword(), grantedAuthorities);
    }
}
