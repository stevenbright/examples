package com.truward.hdbdemo.dal.services;

import com.truward.hdbdemo.dal.beans.Profile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class DummyService implements Service {
    private Logger log = Logger.getLogger(DummyService.class);

    @Autowired
    private HashCalculator hashCalculator;

    public DummyService() {
        log.info("Dummy service created");
    }

    public List<Profile> getSampleProfiles() {
        log.info("Getting sample profiles");

        final List<Profile> result = new ArrayList<Profile>();
        final class Helper {
            void addPerson(String username, String password, int age) {
                final Profile p = new Profile();
                p.setUsername(username);
                p.setPasswordHash(hashCalculator.calcHash(password));
                p.setEmail(username + "@mail.com");
                p.setSubscribedToNews(age % 2 == 0);
                p.setAge(age);
                result.add(p);
            }
        }
        final Helper helper = new Helper();
        helper.addPerson("alice", "123", 18);
        helper.addPerson("bob", "qwerty", 22);
        return result;
    }
}
