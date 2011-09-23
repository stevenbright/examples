package com.alexshabanov;

import com.alexshabanov.service.UserService;
import com.alexshabanov.service.domain.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

import static com.alexshabanov.util.DomainUtil.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/service-test-context.xml" })
@Transactional
public class ServiceTest {
    @Resource
    private UserService userService;


    @Test
    public void testBalanceChange() {
        final String userName = "userName";
        final BigDecimal balance = BigDecimal.valueOf(30L);
        final int id = userService.addUserAccount(userName, balance);

        final BigDecimal delta = BigDecimal.valueOf(20L);
        userService.addToUserBalance(id, delta);

        // check balance
        final UserAccount account = userService.getUserAccount(userName);
        assertAccountEquals(id, userName, balance.add(delta), account);
    }

    @Test
    public void testDeleteAll() {
        final int maxUsers = 10;
        final Random random = new Random();

        final String[] users = new String[maxUsers];
        final BigDecimal[] balances = new BigDecimal[maxUsers];
        final int[] ids = new int[maxUsers];

        for (int i = 0; i < maxUsers; ++i) {
            users[i] = "user" + i;
            balances[i] = BigDecimal.valueOf(1000 + random.nextInt(1000));
            ids[i] = userService.addUserAccount(users[i], balances[i]);
        }

        for (int i = 0; i < maxUsers; ++i) {
            assertAccountEquals(ids[i], users[i], balances[i],
                    userService.getUserAccount(users[i]));
        }

        userService.deleteAllUsers();

        for (int i = 0; i < maxUsers; ++i) {
            try {
                userService.getUserAccount(users[i]);
                fail();
            } catch (EmptyResultDataAccessException ignored) {
                // OK
            }
        }
    }
}
