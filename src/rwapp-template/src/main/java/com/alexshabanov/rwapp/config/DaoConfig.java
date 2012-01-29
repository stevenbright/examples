package com.alexshabanov.rwapp.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ImportResource("classpath:/spring/jpa-data-context.xml")
public class DaoConfig {
}
