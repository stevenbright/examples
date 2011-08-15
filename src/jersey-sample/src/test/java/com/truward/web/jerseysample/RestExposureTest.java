package com.truward.web.jerseysample;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import com.truward.web.jerseysample.rest.model.Greeting;
import com.truward.web.jerseysample.rest.model.UserProfile;
import com.truward.web.jerseysample.service.HelloService;
import com.truward.web.jerseysample.util.LoggerInstaller;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.ws.rs.core.MediaType;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests REST exposure level exclusively.
 */
public class RestExposureTest extends JerseyTest {

    static {
        // this is to catch up all the JUL messages to the generic logging facade
        LoggerInstaller.install();
    }

    private static ApplicationContext CURRENT_CONTEXT;

    /**
     * This class accesses spring application context
     */
    public static class LocalLoaderListener extends ContextLoaderListener {
        @Override
        public void contextInitialized(ServletContextEvent event) {
            super.contextInitialized(event);
            CURRENT_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        }
    }


    /**
     * Public ctor
     * @throws com.sun.jersey.test.framework.spi.container.TestContainerException On error
     */
    public RestExposureTest() throws TestContainerException {
        super(new WebAppDescriptor.Builder("com.truward.web.jerseysample.rest")
                .contextPath("/")
                .contextParam("contextConfigLocation", "classpath:/springtest/rest-exposure-config.xml")
                .contextListenerClass(LocalLoaderListener.class)
                .build());
    }



    private HelloService helloService;


    // testing code


    @Before
    public void setupBeans() {
        String.valueOf(CURRENT_CONTEXT);
        helloService = CURRENT_CONTEXT.getBean(HelloService.class);
        reset(helloService);
    }

    @Test
    public void testExposure() {
        final String username = "alice";
        final String greetingText = "hello, alice";
        expect(helloService.greetUser(anyObject(String.class), eq(username)))
                .andReturn(greetingText)
                .once();
        replay(helloService);

        final WebResource resource = resource();
        final Greeting newGreet = resource.path("/hello/greeting")
                .queryParam("username", username)
                .type(MediaType.APPLICATION_JSON)
                .post(Greeting.class);

        assertEquals(username, newGreet.getUsername());
        assertEquals(greetingText, newGreet.getGreeting());

        verify(helloService);
    }

    @Test
    public void testProfile() {
        final WebResource resource = resource();
        final UserProfile userProfile = resource.path("/hello/profile")
                .type(MediaType.APPLICATION_JSON)
                .get(UserProfile.class);

        assertNotNull(userProfile);
    }
}
