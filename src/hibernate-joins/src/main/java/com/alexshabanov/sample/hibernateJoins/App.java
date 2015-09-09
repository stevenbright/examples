package com.alexshabanov.sample.hibernateJoins;

import com.alexshabanov.sample.hibernateJoins.model.Person;
import com.alexshabanov.sample.hibernateJoins.service.PersonDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Entry point.
 */
public final class App implements Runnable {
  private final Logger log;
  private final PersonDao.Contract personDao;
  private final SessionFactory sessionFactory;

  public App(PersonDao.Contract personDao, SessionFactory sessionFactory) {
    log = LoggerFactory.getLogger(App.class);
    this.personDao = personDao;
    this.sessionFactory = sessionFactory;
  }

  public static void main(String[] args) {
    initContextOverride(Collections.unmodifiableList(Arrays.asList(args)));

    final String[] contextPaths = {"classpath:/hibernateJoins/spring/app-context.xml"};
    // NOTE: DO NOT refresh context here as it will result in spring not being able to resolve all the imports
    try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(contextPaths, false)) {
      // this makes environment properties known to spring, it should be called PRIOR to app context refresh
      initialize(context);
      context.refresh();
      context.start();

      // get Runnable application bean and run it
      context.getBean("app", Runnable.class).run();
    } catch (Exception e) {
      LoggerFactory.getLogger("App").error("Error while running application", e);
    }
  }

  @Override
  public void run() {
    log.info("Application started");
    int i = personDao.hashCode();
    log.trace("personDao.hc={}", i);

    execInSession(() -> {
      final Person p = personDao.getPersonById(10L);
      log.info("person(10)={}", p);
    });

  }

  //
  // Private
  //

  private void execInSession(Runnable runnable) {
    final Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    try {
      runnable.run();
      tx.commit();
    } finally {
      if (!tx.wasCommitted()) {
        tx.rollback();
      }
    }
  }

  private static void initialize(ConfigurableApplicationContext applicationContext) {
    final List<Resource> resources = new ArrayList<>();

    // use application context to get the resource
    resources.add(applicationContext.getResource("classpath:/hibernateJoins/core.properties"));

    final String contextOverridePath = System.getProperty("app.contextOverride");
    if (contextOverridePath != null) {
      resources.add(applicationContext.getResource(contextOverridePath));
    }

    final Properties properties = new Properties();

    // fill properties
    try {
      for (final Resource resource : resources) {
        if (!resource.exists()) {
          throw new IllegalStateException("Resource [" + resource.getDescription() + "] does not exist");
        }

        PropertiesLoaderUtils.fillProperties(properties, resource);
      }

      // initialize property source based on these properties
      final PropertiesPropertySource propertySource = new PropertiesPropertySource("profile", properties);

      // actually insert property source
      applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
    } catch (IOException e) {
      throw new BeanInitializationException("Error while initializing context with properties", e);
    }
  }

  private static void initContextOverride(List<String> args) {
    int overridePropIndex = args.indexOf("--override") + 1;
    if (overridePropIndex > 0) {
      System.setProperty("app.contextOverride", args.get(overridePropIndex));
    }
  }
}
