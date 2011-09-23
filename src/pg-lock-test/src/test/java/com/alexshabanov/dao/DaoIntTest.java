package com.alexshabanov.dao;


import com.alexshabanov.service.domain.UserAccount;
import com.alexshabanov.service.impl.dao.UserAccountDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/dao-test-context.xml" })
@Transactional
public final class DaoIntTest {
    @Resource
    private UserAccountDao userAccountDao;

    private static void assertAccountEquals(int expectedId, String expectedName, BigDecimal expectedBalance,
                                            UserAccount actual) {
        assertNotNull(actual);

        assertEquals(expectedId, actual.getId());
        assertEquals(expectedName, actual.getName());
        assertEquals(expectedBalance, actual.getBalance());
        assertNotNull(actual.getCreated());
    }

    @Test
    public void testSaveAndGetUser() {
        final String userName = "userName";
        final BigDecimal balance = BigDecimal.valueOf(10023L);
        final int id = userAccountDao.addUserAccount(userName, balance);

        assertAccountEquals(id, userName, balance, userAccountDao.getUserAccountByName(userName));

        final List<UserAccount> accounts = userAccountDao.getUserAccounts(0, 9);
        assertEquals(1, accounts.size());

        assertAccountEquals(id, userName, balance, accounts.get(0));
    }
}
