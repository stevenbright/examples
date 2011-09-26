package com.alexshabanov.service.impl.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashSet;
import java.util.Set;

/**
 * Loads "child" configuration into the given application context.
 * This is a workaround for loading child configuration into the primary application context.
 * TODO: remove when bug will be fixed in further spring releases.
 */
public final class EagerPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {
    private Set<ConfigurableListableBeanFactory> processedBeanFactories = new HashSet<ConfigurableListableBeanFactory>();
    private ConfigurableListableBeanFactory hostBeanFactory;

    /**
     * {@inheritDoc}
     *
     * <p>Eagerly resolves property placeholders so that the bean definitions of the other
     * <code>BeanFactoryPostProcessor</code> instances can be modified before instantiation.</p>
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.hostBeanFactory != null) {
            this.postProcessBeanFactory(this.hostBeanFactory);
        }
    }

    /**
     * {@inheritDoc}
     *
     * Obtains the BeanFactory where bean definitions with unresolved property placeholders are stored.
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.hostBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
        } else {
            this.hostBeanFactory = null;
        }

        super.setBeanFactory(beanFactory);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Resolves property placeholders only if the post processing was not run in {@link #afterPropertiesSet}.</p>
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // process one another bean factory
        if (!processedBeanFactories.contains(beanFactory)) {
            super.postProcessBeanFactory(beanFactory);
            processedBeanFactories.add(beanFactory);
        }
    }
}
