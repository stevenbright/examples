package com.alexshabanov.dao;


import com.alexshabanov.service.domain.UserAccount;
import com.alexshabanov.service.impl.dao.UserAccountDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

import static com.alexshabanov.util.DomainUtil.*;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/dao-test-context.xml" })
@Transactional
public final class DaoIntTest {
    @Resource
    private UserAccountDao userAccountDao;



    @Test
    public void testSaveAndGetUser() {
        final String userName = "userName";
        final BigDecimal balance = BigDecimal.valueOf(10023L);
        final int id = userAccountDao.addUserAccount(userName, balance);

        assertAccountEquals(id, userName, balance, userAccountDao.getUserAccountByName(userName));
        assertAccountEquals(id, userName, balance, userAccountDao.getUserAccountById(id));

        final List<UserAccount> accounts = userAccountDao.getUserAccounts(0, 9);
        assertEquals(1, accounts.size());

        assertAccountEquals(id, userName, balance, accounts.get(0));

        final BigDecimal newBalance = balance.add(BigDecimal.valueOf(43534));
        userAccountDao.updateUserBalance(id, newBalance);
        assertAccountEquals(id, userName, newBalance, userAccountDao.getUserAccountById(id));

        // select for update test
        assertAccountEquals(id, userName, newBalance, userAccountDao.getUserAccountByIdForUpdate(id));
    }

    @Test
    public void testDeleteUser() {
        final String userName1 = "userName1";
        final BigDecimal balance1 = BigDecimal.valueOf(10);
        final int id1 = userAccountDao.addUserAccount(userName1, balance1);

        final String userName2 = "userName2";
        final BigDecimal balance2 = BigDecimal.valueOf(10);
        final int id2 = userAccountDao.addUserAccount(userName2, balance2);

        assertAccountEquals(id1, userName1, balance1, userAccountDao.getUserAccountByName(userName1));
        assertAccountEquals(id2, userName2, balance2, userAccountDao.getUserAccountByName(userName2));

        {
            final List<UserAccount> accounts = userAccountDao.getUserAccounts(0, 9);
            assertEquals(2, accounts.size());
            assertAccountEquals(id1, userName1, balance1, accounts.get(0));
            assertAccountEquals(id2, userName2, balance2, accounts.get(1));
        }

        userAccountDao.deleteUser(id2);

        assertAccountEquals(id1, userName1, balance1, userAccountDao.getUserAccountByName(userName1));
        try {
            userAccountDao.getUserAccountByName(userName2);
            fail();
        } catch (EmptyResultDataAccessException ignored) {
            // ok
        }

        {
            final List<UserAccount> accounts = userAccountDao.getUserAccounts(0, 9);
            assertEquals(1, accounts.size());
            assertAccountEquals(id1, userName1, balance1, accounts.get(0));
        }
    }

    @Test
    public void testOffsetLimit() {
        final int maxUsers = 10;
        final Random random = new Random();

        final String[] users = new String[maxUsers];
        final BigDecimal[] balances = new BigDecimal[maxUsers];
        final int[] ids = new int[maxUsers];

        for (int i = 0; i < maxUsers; ++i) {
            users[i] = "user" + i;
            balances[i] = BigDecimal.valueOf(1000 + random.nextInt(1000));
            ids[i] = userAccountDao.addUserAccount(users[i], balances[i]);
        }

        for (int i = 0; i < maxUsers; ++i) {
            assertAccountEquals(ids[i], users[i], balances[i],
                    userAccountDao.getUserAccountByName(users[i]));
            assertAccountEquals(ids[i], users[i], balances[i],
                    userAccountDao.getUserAccountById(ids[i]));
        }

        // 0..maxUsers
        {
            final List<UserAccount> accounts = userAccountDao.getUserAccounts(0, maxUsers);
            assertEquals(maxUsers, accounts.size());

            for (int i = 0; i < maxUsers; ++i) {
                assertAccountEquals(ids[i], users[i], balances[i], accounts.get(i));
            }
        }

        // 0..2
        {
            final List<UserAccount> accounts = userAccountDao.getUserAccounts(0, 2);
            assertEquals(2, accounts.size());

            for (int i = 0; i < 2; ++i) {
                assertAccountEquals(ids[i], users[i], balances[i], accounts.get(i));
            }
        }

        // 2..3
        {
            final List<UserAccount> accounts = userAccountDao.getUserAccounts(2, 3);
            assertEquals(3, accounts.size());

            for (int i = 2; i < 5; ++i) {
                assertAccountEquals(ids[i], users[i], balances[i], accounts.get(i - 2));
            }
        }

        // 5..BIG
        {
            final List<UserAccount> accounts = userAccountDao.getUserAccounts(5, 10 * maxUsers);
            assertEquals(maxUsers - 5, accounts.size());

            for (int i = 5; i < maxUsers; ++i) {
                assertAccountEquals(ids[i], users[i], balances[i], accounts.get(i - 5));
            }
        }
    }
}
