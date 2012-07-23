package com.alexshabanov.springjmsdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({
        "classpath:/spring/activemq-jms-producer-context.xml",
        "classpath:/spring/activemq-jms-listener-context.xml"
})
public class JmsConfig {
}
