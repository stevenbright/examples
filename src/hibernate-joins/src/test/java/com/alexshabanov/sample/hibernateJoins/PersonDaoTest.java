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
}
