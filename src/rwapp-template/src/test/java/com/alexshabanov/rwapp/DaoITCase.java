package com.alexshabanov.rwapp;

import com.alexshabanov.rwapp.config.DaoConfig;
import com.alexshabanov.rwapp.model.user.UserAccount;
import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.model.user.UserRole;
import com.alexshabanov.rwapp.service.dao.UserAccountDao;
import com.alexshabanov.rwapp.service.dao.UserProfileDao;
import com.alexshabanov.rwapp.service.dao.UserRoleDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests integrated DAO configuration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DaoITCase {
    @Autowired
    private UserProfileDao profileDao;

    @Autowired
    private UserAccountDao accountDao;

    @Autowired
    private UserRoleDao roleDao;

    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";
    
    private Set<UserRole> roles(String... codes) {
        final Set<UserRole> result = new HashSet<UserRole>();
        for (final String code : codes) {
            final UserRole role = roleDao.findByCode(code);
            assertNotNull(role);
            assertTrue(result.add(role));
        }
        return result;
    }

    @Before
    public void initRoles() {
        roleDao.save(new UserRole(USER_ROLE));
        roleDao.save(new UserRole(ADMIN_ROLE));
    }


    @Test
    public void testProfile() {
        // save profile #1
        final UserProfile p1 = new UserProfile("pass1", Collections.<UserAccount>emptyList(), roles(USER_ROLE));
        final long id1 = profileDao.save(p1).getId();

        // save profile #2
        final String profile2Email = "profile2@test.com";
        final UserProfile p2 = new UserProfile(
                "pass2",
                Arrays.asList(
                        new UserAccount("profile2@test.com", UserAccount.Kind.EMAIL),
                        new UserAccount("12003332211", UserAccount.Kind.PHONE),
                        new UserAccount("bebop", UserAccount.Kind.NICKNAME)
                ),
                roles(USER_ROLE, ADMIN_ROLE)
        );
        final long id2 = profileDao.save(p2).getId();
        assertNotSame(id1, id2);

        final UserProfile expectedP2  = new UserProfile(id2, p2.getPassword(), p2.getAccounts(), p2.getRoles(), p2.getCreated());
        assertEquals(expectedP2, profileDao.findOne(id2));

        final UserAccount a21 = accountDao.findByAlias(profile2Email);
        assertNotNull(a21);

        for (final UserAccount account : p2.getAccounts()) {
            assertEquals(expectedP2, profileDao.findByAccountAlias(account.getAlias()));
        }
    }
}
