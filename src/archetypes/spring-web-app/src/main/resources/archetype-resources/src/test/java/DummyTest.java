package ${package};

import ${package}.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DummyTest.Config.class)
public class DummyTest {

    @Configuration
    @ComponentScan(basePackages = "${package}.service")
    public static class Config {}

    @Autowired
    private HelloService helloService;

    @Test
    public void testDummy() {
        assertTrue(true);
    }

    @Test
    public void testHelloService() {
        assertNotNull(helloService.getGreeting("test"));
    }
}
