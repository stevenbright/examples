package com.alexshabanov.rwapp;

import com.alexshabanov.rwapp.config.DaoConfig;
import com.alexshabanov.rwapp.service.HelloService;
import com.alexshabanov.rwapp.service.UserService;
import com.alexshabanov.rwapp.service.support.HelloServiceImpl;
import com.alexshabanov.rwapp.service.support.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Non-transactional UT for testing the transaction aspect
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DaoConfig.class, TxITCase.Config.class })
public class TxITCase {
    @Configuration
    public static class Config {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public HelloService helloService() {
            return new HelloServiceImpl();
        }
    }
    
    @Autowired
    private UserService userService;

    @Autowired
    private HelloService helloService;
    
    @Test
    public void testTx() {
        assertNotNull(helloService.getGreeting("asf"));
        assertNull(userService.findProfileByAlias("asd"));
    }
}
