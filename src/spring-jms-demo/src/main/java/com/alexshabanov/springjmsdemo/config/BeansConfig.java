package com.alexshabanov.springjmsdemo.config;

import com.alexshabanov.springjmsdemo.service.AppRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
@ComponentScan(scopedProxy = ScopedProxyMode.INTERFACES, basePackageClasses = AppRunner.class)
public class BeansConfig {
}
