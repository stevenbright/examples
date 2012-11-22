package com.alexshabanov.ritest.config;

import com.alexshabanov.ritest.controller.ExposureController;
import com.alexshabanov.ritest.controller.PublicController;
import com.alexshabanov.ritest.exposure.client.GreetingServiceRestClient;
import com.alexshabanov.ritest.service.GreetingService;
import com.alexshabanov.ritest.service.GreetingServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * Web application configuration.
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Value("${ri.app.useRemoteAccessedGreeting}")
    private boolean useRemoteAccessedGreeting;

    @Value("${ri.app.useRemoteExposedGreeting}")
    private boolean useRemoteExposedGreeting;

    @Value("${ri.app.greetingRemoteUrl}")
    private String greetingRemoteUrl;

    @Bean
    public ViewResolver viewResolver() {
        final UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
        handlerMapping.setAlwaysUseFullPath(true);
        return handlerMapping;
    }

    @Bean
    public HttpMessageConverter<?> jacksonHttpMessageConverter() {
        return new MappingJacksonHttpMessageConverter();
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonHttpMessageConverter());
    }

    //
    // beans
    //

    @Bean
    public GreetingService greetingServiceImpl() {
        return new GreetingServiceImpl();
    }

    @Bean
    public RestOperations restOperations() {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(200);

        final RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setMessageConverters(
                Collections.<HttpMessageConverter<?>>singletonList(jacksonHttpMessageConverter()));

        return restTemplate;
    }

    @Bean
    public GreetingService greetingServiceClient() {
        return new GreetingServiceRestClient(greetingRemoteUrl, restOperations());
    }

    @Bean
    public PublicController publicController() {
        return new PublicController(useRemoteAccessedGreeting ? greetingServiceClient() : greetingServiceImpl());
    }

    @Bean
    public ExposureController exposureController() {
        return new ExposureController(useRemoteExposedGreeting ? greetingServiceClient() : greetingServiceImpl());
    }
}
