package com.truward.web.hello;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

/**
 * /sample URL handler
 */
public class SampleResource extends ServerResource {

    @Autowired
    private Properties appProperties;

    @Get("json")
    public String getResource()  {
        return "{ \"value\": \"" + "REST: " + appProperties.getProperty("helloMessage") + "\" }";
    }
}
