package com.alexshabanov.cameldemo.listener.config;

import com.alexshabanov.cameldemo.listener.controller.PublicController;
import com.alexshabanov.cameldemo.listener.service.support.GreetingSinkServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Application components configuration.
 */
@Configuration
@ComponentScan(basePackageClasses = {PublicController.class, GreetingSinkServiceImpl.class})
public class ComponentConfig {
}
