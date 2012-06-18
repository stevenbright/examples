package com.alexshabanov.txtest;

import com.alexshabanov.txtest.service.AppService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Sample spring-driven test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppServiceTest.Config.class)
public class AppServiceTest {

    @Configuration
    @ComponentScan(basePackages = "com.alexshabanov.txtest.service.support")
    public static class Config {}

    @Autowired
    private AppService appService;

    @Test
    public void testDummy() {
        assertTrue(true);
    }

    @Test
    public void testHello() {
        assertNotNull(appService);
    }
}
