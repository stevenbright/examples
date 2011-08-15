package com.truward.hiberdemo.services;

import com.truward.hiberdemo.beans.Person;
import com.truward.hiberdemo.beans.Role;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Data access object
 */
public final class Dao {
    public Dao() {
    }

    public int addRole(String name) {
        final Role role = new Role();
        role.setName(name);

        final Session session = HibernateAccessor.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(role);
        session.getTransaction().commit();

        return role.getId();
    }

    public int addPerson(String name, int age) {
        final Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setCreated(new Date());

        final Session session = HibernateAccessor.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(person);
        session.getTransaction().commit();

        return person.getId();
    }

    public void addRoleToPerson(int personId, int roleId) {
        final Session session = HibernateAccessor.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        final Person person = (Person) session.load(Person.class, personId);
        final Role role = (Role) session.load(Role.class, roleId);

        person.getRoles().add(role);

        session.getTransaction().commit();
    }

    public List getAllPersons() {
        Session session = HibernateAccessor.getSessionFactory().getCurrentSession();

        session.beginTransaction();
        final List result = session.createCriteria(Person.class).list();
        session.getTransaction().commit();

        return result;
    }

    public List getAllRoles() {
        final Session session = HibernateAccessor.getSessionFactory().getCurrentSession();

        session.beginTransaction();
        final List result = session.createCriteria(Role.class).list();
        //final List result = session.createQuery("from Role").list();
        session.getTransaction().commit();

        return result;
    }
}
