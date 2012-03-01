package com.alexshabanov.rwapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/spring/jpa-data-context.xml")
public class DaoConfig {
}
