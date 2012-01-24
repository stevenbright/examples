package com.alexshabanov.rwapp;

import com.alexshabanov.rwapp.model.user.UserAccount;
import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.service.dao.UserAccountDao;
import com.alexshabanov.rwapp.service.dao.UserProfileDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    
    @Autowired
    private UserAccountDao accountDao;

    @Test
    public void testProfile() {
        // save profile #1
        final UserProfile p1 = new UserProfile(null, Collections.<UserAccount>emptyList());
        final long id1 = profileDao.save(p1).getId();

        // save profile #2
        final String profile2Email = "profile2@test.com";
        final UserProfile p2 = new UserProfile(null, Arrays.asList(
                new UserAccount(null, "profile2@test.com", UserAccount.Kind.EMAIL)
        ));
        final long id2 = profileDao.save(p2).getId();
        assertNotSame(id1, id2);
        assertEquals(new UserProfile(id2, p2.getAccounts()), profileDao.findOne(id2));

        final UserAccount a21 = accountDao.findByAlias(profile2Email);
        assertNotNull(a21);
    }
}
