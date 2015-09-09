package com.alexshabanov.sample.hibernateJoins.service;

import com.alexshabanov.sample.hibernateJoins.model.Person;
import org.hibernate.SessionFactory;

/**
 * Data access object for person information.
 */
public final class PersonDao {

  public interface Contract {
    Person getPersonById(long id);
  }

  public static final class Impl implements Contract {
    private final SessionFactory sessionFactory;

    public Impl(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
    }

    @Override
    public Person getPersonById(long id) {
      return sessionFactory.getCurrentSession().load(Person.class, id);
    }
  }

  private PersonDao() {} // hidden
}
