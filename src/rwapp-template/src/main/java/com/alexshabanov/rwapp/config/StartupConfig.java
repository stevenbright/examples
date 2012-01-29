package com.alexshabanov.rwapp.config;

import com.alexshabanov.rwapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Initializes initial application data.
 */
@Configuration
public class StartupConfig {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initDefaultUsers() {
        userService.saveRoles("ROLE_USER", "ROLE_ADMIN");
        userService.createProfile("123", "admin", "ROLE_USER", "ROLE_ADMIN");
        userService.createProfile("1", "user1", "ROLE_USER");
    }
}
