package com.mysite.jdort.utils;

import javax.jdo.JDOEnhancer;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import java.io.IOException;
import java.util.Map;

/**
 * Creates persistent manager factory (persistence.xml in META-INF is used).
 */
public final class RuntimeJdoEnhancer {
    private static void addAndEnhanceClasses(JDOEnhancer enhancer,
                                             RedefinableClassLoader classLoader,
                                             String[] classes) throws IOException {

        // add class and it's content in order to enhance it further
        for (final String className : classes) {
            enhancer.addClass(className, ClassUtils.loadClassAsByteArray(className));
        }

        // perform the enhancement operation
        enhancer.enhance();

        // reload the updated classes definitions
        for (final String className : classes) {
            final byte[] enhancedDef = enhancer.getEnhancedBytes(className);
            classLoader.defineClass(className, enhancedDef);
        }
    }

    public static PersistenceManagerFactory createEnhancedPmf(RedefinableClassLoader classLoader,
                                                              Map<?, ?> properties,
                                                              String ... domainClasses) throws IOException {
        // Use the standard JDO enhancer
        final JDOEnhancer enhancer = JDOHelper.getEnhancer();
        enhancer.setVerbose(true);
        enhancer.setClassLoader(classLoader);

        addAndEnhanceClasses(enhancer, classLoader, domainClasses);

        final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(properties, classLoader);
        return pmf;
    }


    /**
     * Hidden ctor
     */
    private RuntimeJdoEnhancer() {}
}
