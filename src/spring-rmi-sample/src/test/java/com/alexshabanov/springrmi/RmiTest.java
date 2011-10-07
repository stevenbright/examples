package com.alexshabanov.springrmi;

import com.alexshabanov.springrmi.model.Hello;
import com.alexshabanov.springrmi.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/rmi-test-context.xml" })
public final class RmiTest {
    @Resource(name = "clientSideHelloService")
    private HelloService helloService;

    @Test
    public void testRemoteExposure() {
        final Hello greeting = helloService.getGreeting("Test");
        assertNotNull(greeting);
    }
}
