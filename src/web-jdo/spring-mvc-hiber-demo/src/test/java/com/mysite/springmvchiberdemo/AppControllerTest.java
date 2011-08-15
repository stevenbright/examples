package com.mysite.springmvchiberdemo;

import com.mysite.springmvchiberdemo.controller.AppController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/service-context.xml", "/webmvc-context.xml" })
public class AppControllerTest {
    @Resource
    private AppController appController;

    @Test
    public void testPageRetrieval() {
        // direct method invokation
        final ModelAndView mvHello = appController.gethello(
                new MockHttpServletRequest(), new MockHttpServletResponse());
        final ModelAndView mvIndex = appController.getindex(
                new MockHttpServletRequest(), new MockHttpServletResponse());

        Assert.assertTrue(mvHello.getViewName().contains("hello"));
        Assert.assertTrue(mvIndex.getViewName().contains("index"));
    }
}
