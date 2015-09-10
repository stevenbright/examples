package com.alexshabanov.sample.hibernateJoins.service;

import com.alexshabanov.sample.hibernateJoins.model.Person;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

/**
 * Data access object for person information.
 */
public final class PersonDao {

  public interface Contract {
    Person getPersonById(long id);

    Long savePerson(Long id, String name, int age);

    List<Person> getPersonsOnPositionInCompany(String position, String company, Date when);
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

    @Override
    public List<Person> getPersonsOnPositionInCompany(String position, String company, Date when) {
      final SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery("SELECT p.id, p.name, p.age " +
          "FROM employment_entry ee " +
          "INNER JOIN person p ON ee.person_id=p.id " +
          "INNER JOIN company c ON ee.company_id=c.id " +
          "INNER JOIN company_position cp ON ee.pos_id=cp.id " +
          "WHERE (:date >= ee.start_date) AND ((ee.end_date IS NULL) OR (:date <= ee.end_date)) " +
          "AND :company=c.name AND :position=cp.name");

      sqlQuery.addEntity(Person.class);

      sqlQuery.setString("company", company);
      sqlQuery.setString("position", position);
      sqlQuery.setDate("date", when);

      @SuppressWarnings("unchecked") final List<Person> persons = (List<Person>) sqlQuery.list();

      return persons;
    }
  }

  private PersonDao() {} // hidden
}
