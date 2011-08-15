package com.truward.hiberdemo;

import com.truward.hiberdemo.services.Dao;
import com.truward.hiberdemo.services.HibernateAccessor;
import org.apache.log4j.Logger;

/**
 * Entry point
 */
public class App {
    private static Logger log = Logger.getLogger(App.class);

    private static void storeSampleData(Dao dao) {
        final int adminRoleId = dao.addRole("ADMIN");
        final int moderatorRoleId = dao.addRole("MODERATOR");
        final int expertRoleId = dao.addRole("EXPERT");
        final int userRoleId = dao.addRole("USER");

        final int aliceId = dao.addPerson("alice", 18);
        final int bobId = dao.addPerson("bob", 25);
        final int cavinId = dao.addPerson("cavin", 22);

        dao.addRoleToPerson(aliceId, userRoleId);
        dao.addRoleToPerson(bobId, expertRoleId);
        dao.addRoleToPerson(bobId, moderatorRoleId);
        dao.addRoleToPerson(cavinId, adminRoleId);
    }
    

    private static void printAll(Iterable iterable) {
        for (final Object value : iterable) {
            log.info(value.toString());
        }

    }

    public static void main(String[] args) {
        log.debug("Hiber demo app start");

        final Dao dao = new Dao();

        storeSampleData(dao);

        printAll(dao.getAllRoles());
        printAll(dao.getAllPersons());

        // close hibernate session
        HibernateAccessor.getSessionFactory().close();
    }
}
