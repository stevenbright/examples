package net.threadtxtest.service.internal.conf;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Configuration manager that takes care of the initialization of the child context.
 */
public final class TxtestConfigurationManager implements ApplicationContextAware {

    public static enum DaoKind {
        HSQLDB("classpath:/spring/internal/dao/hsqldb-dao-context.xml"),
        MYSQL("classpath:/spring/internal/dao/mysql-dao-context.xml"),
        ORACLE("classpath:/spring/internal/dao/oracle-dao-context.xml");

        private final String contextConfigLocation;

        public String getContextConfigLocation() {
            return contextConfigLocation;
        }

        DaoKind(String contextConfigLocation) {
            this.contextConfigLocation = contextConfigLocation;
        }
    }

    private static final String CHILD_CONTEXT_LOCATION = "classpath:/spring/internal/child-context.xml";

    private DaoKind daoKind;

    private ApplicationContext childApplicationContext;


    public void setDaoKind(DaoKind daoKind) {
        this.daoKind = daoKind;
    }


    public ApplicationContext getChildApplicationContext() {
        if (childApplicationContext == null) {
            throw new IllegalStateException("Can't get child application context - " +
                    "the configuration manager is not aware of the parent application context");
        }
        return childApplicationContext;
    }

    /**
     * {@inheritDoc}
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        childApplicationContext = new ClassPathXmlApplicationContext(new String[] {
                daoKind.getContextConfigLocation(),
                CHILD_CONTEXT_LOCATION,
        }, applicationContext);
    }
}
