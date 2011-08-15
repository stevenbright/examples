package com.mysite.jdort;

import com.mysite.jdort.rtone.InitializableRunner;
import com.mysite.jdort.rtone.PersistenceTester;
import com.mysite.jdort.rtsec.DaoTester;
import com.mysite.jdort.rtsec.SampleDao;
import com.mysite.jdort.rtsec.SampleDaoImpl;
import com.mysite.jdort.utils.RedefinableClassLoader;
import com.mysite.jdort.utils.RuntimeJdoEnhancer;

import javax.jdo.PersistenceManagerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry point
 */
public class App {

    private static void loadPersistenceTester(
            PersistenceManagerFactory persistenceManagerFactory,
            RedefinableClassLoader classLoader) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        final InitializableRunner initializableRunner = classLoader.reloadAndCreate(PersistenceTester.class);

        final Map<String, Object> props = new HashMap<String, Object>();
        props.put("persistenceManagerFactory", persistenceManagerFactory);
        initializableRunner.init(props);

        initializableRunner.run();
    }

    public static Map<?, ?> getJdoProps() {
        final Map<String, Object> props = new HashMap<String, Object>();

        props.put("javax.jdo.PersistenceManagerFactoryClass","org.datanucleus.jdo.JDOPersistenceManagerFactory");
        props.put("datanucleus.ConnectionDriverName","org.hsqldb.jdbcDriver");
        props.put("datanucleus.ConnectionURL","jdbc:hsqldb:mem:jdo-rt-db");
        props.put("datanucleus.ConnectionUserName","sa");
        props.put("datanucleus.ConnectionPassword","");
        props.put("datanucleus.autoStartMechanism","None");
        props.put("datanucleus.autoCreateSchema","true");
        props.put("datanucleus.autoCreateTables","true");
        props.put("datanucleus.autoCreateColumns","true");
        props.put("datanucleus.rdbms.stringDefaultLength","255");

        return props;
    }

    public static PersistenceManagerFactory createPmf(RedefinableClassLoader classLoader) throws IOException {
        return RuntimeJdoEnhancer.createEnhancedPmf(classLoader,
                getJdoProps(),
                "com.mysite.jdort.model.Account",
                "com.mysite.jdort.model.BlogItem");
    }

    public static void runEnhancerSample(RedefinableClassLoader classLoader) throws Exception {
        final PersistenceManagerFactory persistenceManagerFactory = createPmf(classLoader);
        loadPersistenceTester(persistenceManagerFactory, classLoader);
    }

    public static void runDaoSample(RedefinableClassLoader classLoader) throws Exception {
        final PersistenceManagerFactory persistenceManagerFactory = createPmf(classLoader);

        final SampleDao sampleDao = classLoader.reloadAndCreate(SampleDaoImpl.class);
        sampleDao.setPersistenceManagerFactory(persistenceManagerFactory);

        DaoTester tester = new DaoTester(sampleDao);
        tester.test();
    }


    public static void main(String[] args) {
        final RedefinableClassLoader classLoader = new RedefinableClassLoader(Thread.currentThread().getContextClassLoader());

        try {
            if (args.length > 10) {
                runEnhancerSample(classLoader);
            } else {
                runDaoSample(classLoader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
