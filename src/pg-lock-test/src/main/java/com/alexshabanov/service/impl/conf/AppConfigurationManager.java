package com.alexshabanov.service.impl.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;

/**
 * Application configuration manager that adds "child" configuration that varies depending on
 * the properties specified.
 */
public final class AppConfigurationManager implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {
    /**
     * Represents various DAO kinds used within the application.
     * The enum names must match the specified in the app-*.property files
     */
    public static enum DaoKind {
        POSTGRESQL("/spring/internal/dao/postgresql-dao-context.xml"),
        ORACLE("/spring/internal/dao/oracle-dao-context.xml");

        private final String contextLocation;

        public String getContextLocation() {
            return contextLocation;
        }

        DaoKind(String contextLocation) {
            this.contextLocation = contextLocation;
        }
    }

    private static final String INFERIOR_CONTEXT_LOCATION = "/spring/internal/inferior-context.xml";
    private String daoContextLocation;
    private ApplicationContext hostContext;
    private PropertyPlaceholderConfigurer hostPropertyConfigurer;


    /**
     * This methods introduced to force spring load property placeholder configurer <b>before</b> this class' bean.
     * If this was not done, it is not guaranteed that #setDaoKind will be called
     * with the properly substituted argument.
     *
     * TODO: remove this setter
     *
     * @param configurer Eager property placeholder configurer.
     */
    public void setPropertyConfigurer(PropertyPlaceholderConfigurer configurer) {
        hostPropertyConfigurer = configurer;
    }


    public void setDaoKind(DaoKind daoKind) {
        daoContextLocation = daoKind.getContextLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        hostContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     *
     * Add all the inferior context's beans definitions.
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(hostContext);
        final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        reader.loadBeanDefinitions(new ClassPathResource(daoContextLocation),
                new ClassPathResource(INFERIOR_CONTEXT_LOCATION));

        hostPropertyConfigurer.postProcessBeanFactory(beanFactory);

        for (final String beanName : reader.getBeanFactory().getBeanDefinitionNames()) {
            registry.registerBeanDefinition(beanName, reader.getBeanFactory().getBeanDefinition(beanName));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }
}
