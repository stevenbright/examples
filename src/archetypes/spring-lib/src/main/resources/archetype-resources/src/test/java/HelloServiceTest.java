package ${package};

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
@ContextConfiguration(classes = HelloServiceTest.Config.class)
public class HelloServiceTest {

    @Configuration
    @ComponentScan(basePackages = "${package}")
    public static class Config {}

    @Autowired
    private HelloService helloService;

    @Test
    public void testDummy() {
        assertTrue(true);
    }

    @Test
    public void testHello() {
        assertEquals(3, helloService.add(1, 2));
    }
}
