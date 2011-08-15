package com.alexshabanov.txtest.service.impl;

import com.alexshabanov.txtest.service.BankService;
import com.alexshabanov.txtest.service.exception.InsufficientFundsException;
import com.alexshabanov.txtest.service.exception.ReplenishmentOverrunException;
import com.alexshabanov.txtest.service.exception.ServiceDaoException;
import com.alexshabanov.txtest.service.impl.dao.BankDao;
import com.alexshabanov.txtest.service.model.BalanceChange;
import com.alexshabanov.txtest.service.model.BalanceChangeEntry;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Random;

/**
 * Implementation of the bank service.
 */
public final class BankServiceImpl implements BankService {
    @Resource
    private BankDao bankDao;

    private double maxReplenishmentAmount = 5000.0;



    private static final long RETRY_MSEC_MIN = 100;

    private static final int RETRY_MSEC_DELTA = 50;

    private static final int RETRY_COUNT = 40;

    private static final Random retryRandom = new Random();


    private static void performWithRetry(Runnable runnable) {
        boolean failed = true;
        int retries = RETRY_COUNT;
        do {
            try {
                runnable.run();
                failed = false;
            } catch (ConcurrencyFailureException e) {
                // pessimistic lock failure - try to retry operation again after a while
                --retries;
                if (retries <= 0) {
                    throw new ServiceDaoException("concurrent retries exceeded", e);
                }

                // sleep random interval
                try {
                    Thread.sleep(RETRY_MSEC_MIN + retryRandom.nextInt(RETRY_MSEC_DELTA));
                } catch (InterruptedException e1) {
                    // ignore interruption errors
                }
            }
        } while (failed);
    }

    /**
     * {@inheritDoc}
     */
    public double getMaxReplenishmentAmount() {
        return maxReplenishmentAmount;
    }

    /**
     * {@inheritDoc}
     */
    public void setMaxReplenishmentAmount(double maxReplenishmentAmount) {
        this.maxReplenishmentAmount = maxReplenishmentAmount;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void withdraw(final long userId, final double amount) {
        performWithRetry(new Runnable() {
            public void run() {
                final double newBalance = bankDao.getBalance(userId) - amount;

                // artificial interval before update to get collisions
                try {
                    Thread.sleep(100 + retryRandom.nextInt(50));
                } catch (InterruptedException e) {
                    // ignore
                }

                bankDao.setBalance(userId, newBalance);

                bankDao.addBalanceChange(userId, amount, BalanceChange.REPLENISHMENT);

                final double balance = bankDao.getBalance(userId);

                if (balance < 0) {
                    throw new InsufficientFundsException("Too few money when withdrawing amount = " + amount);
                }


//                bankDao.addToBalance(userId, -amount);
//                bankDao.addBalanceChange(userId, amount, BalanceChange.REPLENISHMENT);
//
//                final double balance = bankDao.getBalance(userId);
//
//                if (balance < 0) {
//                    throw new InsufficientFundsException("Too few money when withdrawing amount = " + amount);
//                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void replenish(final long userId, final double amount) {
        performWithRetry(new Runnable() {
            public void run() {
                final double newBalance = bankDao.getBalance(userId) + amount;

                // artificial interval before update to get collisions
                try {
                    Thread.sleep(100 + retryRandom.nextInt(50));
                } catch (InterruptedException e) {
                    // ignore
                }

                bankDao.setBalance(userId, newBalance);
                //bankDao.addToBalance(userId, amount);

                bankDao.addBalanceChange(userId, amount, BalanceChange.REPLENISHMENT);

                if (amount > getMaxReplenishmentAmount()) {
                    throw new ReplenishmentOverrunException("Too big replenishment amount: " + amount);
                }


//                bankDao.addToBalance(userId, amount);
//                bankDao.addBalanceChange(userId, amount, BalanceChange.REPLENISHMENT);
//
//                if (amount > getMaxReplenishmentAmount()) {
//                    throw new ReplenishmentOverrunException("Too big replenishment amount: " + amount);
//                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public double getBalance(long userId) {
        return bankDao.getBalance(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public long registerUser(String name) {
        return bankDao.addUser(name);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Collection<BalanceChangeEntry> queryBalanceChanges(long userId) {
        return bankDao.getBalanceChanges(userId);
    }
}
