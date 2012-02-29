package com.alexshabanov.springintdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/service-test-context.xml" })
public class HelloServiceTest {

    @Test
    public void testDummy() {
        assertTrue(true);
    }
}
