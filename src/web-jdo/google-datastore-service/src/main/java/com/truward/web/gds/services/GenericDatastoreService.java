package com.truward.web.gds.services;


import com.google.appengine.api.datastore.*;
import com.truward.web.gds.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericDatastoreService {
    private static final String ENTITY_PERSON = "person";

    private static final String FIELD_DISPLAY_NAME = "displayName";

    private static final String FIELD_BALANCE = "balance";

    private static final String FIELD_AGE = "age";

    private static final String FIELD_STATUS = "status";


    private static final Logger LOG = LoggerFactory.getLogger(GenericDatastoreService.class);


    private final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    private PreparedQuery personQuery;

    private PreparedQuery getPersonQuery() {
        if (personQuery == null) {
            final Query query = new Query(ENTITY_PERSON);
            query.addSort(FIELD_DISPLAY_NAME);
            personQuery = datastoreService.prepare(query);
        }

        return personQuery;
    }

    private static Person convertToPerson(Entity entity) {
        final long id = entity.getKey().getId();
        final String displayName = (String) entity.getProperty(FIELD_DISPLAY_NAME);
        final double balance = (Double) entity.getProperty(FIELD_BALANCE);
        final int age = (int)(long)(Long) entity.getProperty(FIELD_AGE);
        final String status = (String) entity.getProperty(FIELD_STATUS);

        return new Person() {
            public long getId() {
                return id;
            }

            public String getDisplayName() {
                return displayName;
            }

            public double getBalance() {
                return balance;
            }

            public int getAge() {
                return age;
            }

            public String getStatus() {
                return status;
            }
        };
    }


    public long savePerson(String displayName, double balance, int age, String status) {
        final Entity entity = new Entity(ENTITY_PERSON);
        entity.setProperty(FIELD_DISPLAY_NAME, displayName);
        entity.setProperty(FIELD_BALANCE, balance);
        entity.setProperty(FIELD_AGE, age);
        entity.setProperty(FIELD_STATUS, status);
        final Key key = datastoreService.put(entity);
        return key.getId();
    }

    public Person getPerson(long id) {
        try {
            return convertToPerson(datastoreService.get(
                    KeyFactory.createKey(ENTITY_PERSON, id)));
        } catch (EntityNotFoundException e) {
            LOG.trace("No such entity", e);
            return null;
        }
    }

    public Collection<Person> getPersons() {
        final List<Person> result = new ArrayList<Person>();

        for (final Entity entity : getPersonQuery().asIterable()) {
            result.add(convertToPerson(entity));
        }

        return result;
    }
}
