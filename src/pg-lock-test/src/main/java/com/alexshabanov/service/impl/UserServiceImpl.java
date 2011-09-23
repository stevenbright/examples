package com.alexshabanov.service.impl;

import com.alexshabanov.service.UserService;
import com.alexshabanov.service.domain.UserAccount;
import com.alexshabanov.service.impl.dao.UserAccountDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Transactional
public final class UserServiceImpl implements UserService {
    @Resource
    private UserAccountDao userAccountDao;

    private long updateBalanceDelay = 1L;

    private boolean pessimisticLock = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUpdateOptions(long delayMillis, boolean pessimisticLock) {
        updateBalanceDelay = delayMillis;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToUserBalance(int id, BigDecimal balanceDelta) {
        final UserAccount account;


        if (pessimisticLock) {
            account = userAccountDao.getUserAccountByIdForUpdate(id);
        } else {
            account = userAccountDao.getUserAccountById(id);
        }

        try {
            Thread.sleep(updateBalanceDelay);
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }

        final BigDecimal newBalance = account.getBalance().add(balanceDelta);

        userAccountDao.updateUserBalance(id, newBalance);
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
