package com.alexshabanov.sample.hibernateJoins.context;

import com.alexshabanov.sample.hibernateJoins.model.Person;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Creates a session factory as described in
 * http://www.journaldev.com/2882/hibernate-tutorial-for-beginners-using-xml-annotations-and-property-configurations
 */
public final class SessionFactoryInitializer {

  public static SessionFactory createSessionFactory(DataSource dataSource) {
    final LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setAnnotatedClasses(Person.class);

    final Properties props = new Properties();
    props.put("hibernate.connection.driver_class", "org.h2.Driver");
    props.put("hibernate.connection.url", "jdbc:h2:mem:tempDb;DB_CLOSE_DELAY=-1");
    props.put("hibernate.connection.username", "SA");
    props.put("hibernate.connection.password", "");
    props.put("hibernate.current_session_context_class", "thread");
    factoryBean.setHibernateProperties(props);

    try {
      factoryBean.afterPropertiesSet();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return factoryBean.getObject();
  }
}
