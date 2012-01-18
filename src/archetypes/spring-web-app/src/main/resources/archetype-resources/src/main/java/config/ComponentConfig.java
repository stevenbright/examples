package ${package}.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Application components configuration.
 */
@Configuration
@ComponentScan(basePackages = "${package}")
public class ComponentConfig {
}
