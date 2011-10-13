package com.alexshabanov.sched;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Sample spring-driven test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/service-test-context.xml" })
public class HelloServiceTest {

    /**
     * Sample test method.
     */
    @Test
    public void testDummy() {
        assertTrue(true);
    }

    @Test
    public void testHello() {
        final HelloService s = new HelloService();
        assertEquals(3, s.add(1, 2));
    }
}
