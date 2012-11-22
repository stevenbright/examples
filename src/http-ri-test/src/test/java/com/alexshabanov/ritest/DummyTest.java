package com.alexshabanov.ritest;

import com.alexshabanov.ritest.service.GreetingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DummyTest.Config.class)
public class DummyTest {

    @Configuration
    @ComponentScan(basePackages = "com.alexshabanov.ritest.service")
    public static class Config {}

    @Autowired
    private GreetingService greetingService;

    @Test
    public void testGetHello() {
        assertNotNull(greetingService.getHello("test"));
    }
}
