package com.truward.web.gds;


import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.truward.web.gds.model.Person;
import com.truward.web.gds.services.GenericDatastoreService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class DatastoreServiceTest {
    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private GenericDatastoreService datastoreService;

    @Before
    public void setUpLocalService() {
        helper.setUp();
        datastoreService = new GenericDatastoreService();
    }

    @After
    public void tearDownLocalService() {
        helper.tearDown();
    }


    private static void assertPersonEquals(Person person,
                                           long id,
                                           String displayName, double balance, int age, String status) {
        assertEquals(id, person.getId());
        assertEquals(displayName, person.getDisplayName());
        assertEquals(balance, person.getBalance(), 0.01);
        assertEquals(age, person.getAge());
        assertEquals(status, person.getStatus());
    }

    @Test
    public void testPerson() {
        datastoreService.savePerson("alex", 100.123, 21, "Chessmaster");
        datastoreService.savePerson("bob", 200.1, 22, "Knight");
        datastoreService.savePerson("fred", 99, 17, "Swordsman");
        datastoreService.savePerson("eva", 75.4, 23, "Magician");
        final long idAnn = datastoreService.savePerson("ann", 451.2, 16, "Healer");

        {
            final Collection<Person> persons = datastoreService.getPersons();
            final Person[] p = persons.toArray(new Person[0]);
            assertEquals(5, p.length);

            assertEquals("alex", p[0].getDisplayName());

            assertPersonEquals(p[1], idAnn, "ann", 451.2, 16, "Healer");

            assertPersonEquals(datastoreService.getPerson(idAnn),
                    idAnn, "ann", 451.2, 16, "Healer");

            assertEquals("bob", p[2].getDisplayName());
            assertEquals("eva", p[3].getDisplayName());
            assertEquals("fred", p[4].getDisplayName());
        }
    }
}
