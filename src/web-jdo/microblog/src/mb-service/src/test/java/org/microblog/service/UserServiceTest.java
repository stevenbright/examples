package org.microblog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.microblog.exposure.model.Chunk;
import org.microblog.exposure.model.UserAccount;
import org.microblog.exposure.server.exception.ServiceException;
import org.microblog.exposure.server.service.UserService;
import org.microblog.service.exception.ServiceDaoException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/user-service-test-context.xml" })
@Transactional
public class UserServiceTest {
    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private UserService userService;



    @Test
    public void testSimpleRegistration() {
        final long id1 = userService.registerUser("login1", "password1", "mail1", "avatarUrl1");
        final long id2 = userService.registerUser("login2", "password2", "mail2", "avatarUrl2");
        assertNotSame(id1, id2);

        final Chunk<UserAccount> accounts = userService.getUserAccounts(0, 100);
        assertEquals(2, accounts.getCount());

        final UserAccount account1 = userService.getUserAccount(id1);
        assertEquals(id1, account1.getId());
    }

    @Test(expected = ServiceDaoException.class)
    public void testLoginUniquenessVerificationFailure() {
        try {
            userService.registerUser("login1", "password1", "mail1", "avatarUrl1");
            userService.registerUser("login2", "password2", "mail2", "avatarUrl2");
        } catch (ServiceException e) {
            fail("Unexpected service exception: " + e);
        }

        userService.registerUser("login1", "password3", "mail3", "avatarUrl3");
    }

    @Test
    public void testRegistrationRollback() {
        userService.registerUser("login1", "password1", "mail1", "avatarUrl1");
        userService.registerUser("login2", "password2", "mail2", "avatarUrl2");

        {
            final Chunk<UserAccount> accounts = userService.getUserAccounts(0, 100);
            assertEquals(2, accounts.getCount());
        }

//
//        // transaction template
//        final TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
//
//        final Boolean[] markers = new Boolean[1];
//        try {
//            txTemplate.execute(new TransactionCallback<Object>() {
//                public Object doInTransaction(TransactionStatus status) {
//                    userService.registerUser("login3", "password3", "mail3", null);
//                    markers[0] = true;
//
//                    // the following invocation shall fail
//                    userService.registerUser("login1", "password4", "mail4", null);
//                    fail("Code shall not succeed");
//                    return null;
//                }
//            });
//        } catch (ServiceDaoException e) {
//            assertTrue(e.getCause() instanceof DataAccessException);
//        }
//        // check markers
//        assertEquals(true, markers[0]);
//
//        // check, that commit reverted and we still have two users only
//        {
//            final Chunk<UserAccount> accounts = userService.getUserAccounts(0, 100);
//            assertEquals(2, accounts.getCount());
//        }
    }
}
