package com.alexshabanov.sample.hibernateJoins.service;

import com.alexshabanov.sample.hibernateJoins.model.Person;
import org.hibernate.SessionFactory;

/**
 * Data access object for person information.
 */
public final class PersonDao {

  public interface Contract {
    Person getPersonById(long id);

    Long savePerson(Long id, String name, int age);
  }

  public static final class Impl implements Contract {
    private final SessionFactory sessionFactory;

    public Impl(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
    }

    @Override
    public Person getPersonById(long id) {
      return (Person) sessionFactory.getCurrentSession().load(Person.class, id);
    }

    @Override
    public Long savePerson(Long id, String name, int age) {
      final Person person = new Person();
      person.setName(name);
      person.setAge(age);

      if (id != null) {
        person.setId(id);
        sessionFactory.getCurrentSession().saveOrUpdate(person);
        return id;
      } else {
        return (Long) sessionFactory.getCurrentSession().save(person);
      }
    }
  }

  private PersonDao() {} // hidden
}
