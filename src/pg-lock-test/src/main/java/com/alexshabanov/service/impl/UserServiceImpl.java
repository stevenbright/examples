package com.alexshabanov.service.impl;

import com.alexshabanov.service.UserService;
import com.alexshabanov.service.domain.UserAccount;
import com.alexshabanov.service.impl.dao.UserAccountDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Transactional
public final class UserServiceImpl implements UserService {
    @Resource
    private UserAccountDao userAccountDao;

    private int delayMillis = 10;

    private boolean pessimisticLock = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUsePessimisticLock(boolean pessimisticLock) {
        this.pessimisticLock = pessimisticLock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addUserAccount(String name, BigDecimal balance) {
        return userAccountDao.addUserAccount(name, balance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllUsers() {
        final List<UserAccount> accounts = userAccountDao.getUserAccounts(0, Integer.MAX_VALUE);

        for (final UserAccount account : accounts) {
            userAccountDao.deleteUser(account.getId());
        }
    }

    private final Random random = new Random();

    private static final int DELAY_THRESHOLD = 10;

    private void delay() {
        try {
            Thread.sleep(random.nextInt(delayMillis) + DELAY_THRESHOLD);
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }
    }

    private void addToUserBalanceWithOptimisticLock(int id, BigDecimal balanceDelta) {
        final UserAccount account = userAccountDao.getUserAccountById(id);

        // imitate long calculations and optimistic lock mechanics
        delay();
        {
            // double check for concurrent modification
            final UserAccount presentAccount = userAccountDao.getUserAccountById(id);
            if (!presentAccount.getBalance().equals(account.getBalance())) {
                System.err.println(">> Optimistic lock violation in thread " + Thread.currentThread().getName() +
                        " original: " + account.getBalance() + " current: " + presentAccount.getBalance());
            }
        }
        final BigDecimal newBalance = account.getBalance().add(balanceDelta);

        userAccountDao.updateUserBalance(id, newBalance);
    }

    private void addToUserBalanceWithPessimisticLock(int id, BigDecimal balanceDelta) {
        final UserAccount account = userAccountDao.getUserAccountByIdForUpdate(id);

        // imitate long calculations
        delay();
        final BigDecimal newBalance = account.getBalance().add(balanceDelta);

        userAccountDao.updateUserBalance(id, newBalance);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addToUserBalance(int id, BigDecimal balanceDelta) {
        if (pessimisticLock) {
            addToUserBalanceWithPessimisticLock(id, balanceDelta);
        } else {
            addToUserBalanceWithOptimisticLock(id, balanceDelta);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public UserAccount getUserAccount(String name) {
        return userAccountDao.getUserAccountByName(name);
    }
}
