package com.alexshabanov.rwapp;

import com.alexshabanov.rwapp.model.user.UserAccount;
import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.service.dao.UserProfileDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.Assert.assertNotSame;

/**
 * Tests integrated DAO configuration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/jpa-data-context.xml")
@Transactional
public class DaoITCase {
    @Autowired
    private UserProfileDao profileDao;

    @Test
    public void testProfile() {
        final UserProfile p1 = new UserProfile(null, Collections.<UserAccount>emptyList());

        final long id1 = profileDao.save(p1).getId();
        final long id2 = profileDao.save(p1).getId();
        assertNotSame(id1, id2);
        
        //final UserProfile p2
    }
}
