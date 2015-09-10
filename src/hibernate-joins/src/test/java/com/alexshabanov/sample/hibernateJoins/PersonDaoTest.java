package com.alexshabanov.sample.hibernateJoins;

import com.alexshabanov.sample.hibernateJoins.model.Person;
import com.alexshabanov.sample.hibernateJoins.service.PersonDao;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexander Shabanov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/PersonDaoTest-context.xml")
@Transactional
public final class PersonDaoTest {
  @Resource PersonDao.Contract personDao;
  @Resource SessionFactory sessionFactory;

  @Test
  public void shouldSavePerson() {
    // TODO: get rid of manual tx management - spring should do that for us
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
    try {
      final Long id = personDao.savePerson(null, "zed", 34);
      final Person person = personDao.getPersonById(id);
      assertEquals("zed", person.getName());
      assertEquals(34, person.getAge());

      // The following - won't work - 'org.hibernate.NonUniqueObjectException: a different object with the same identifier value was already associated with the session: [com.alexshabanov.sample.hibernateJoins.model.Person#100]'
      //final Long anotherId = personDao.savePerson(id, "zimm", 52);
      //assertEquals(id, anotherId);
    } finally {
      tx.rollback();
    }
  }

  @Test
  public void shouldGetCustomerWorkingInACompanyOnPosition() {
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
    try {
      final List<Person> persons = personDao.getPersonsOnPositionInCompany("Designer", "Starbucks",
          Date.from(Instant.parse("1995-08-23T10:00:00.00Z")));
      assertEquals(1, persons.size());
      final Person bob = persons.get(0);
      assertEquals("Bob", bob.getName());
      assertEquals(37, bob.getAge());
    } finally {
      tx.rollback();
    }
  }
}
