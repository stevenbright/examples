package com.alexshabanov.txtest.service;

import com.alexshabanov.txtest.service.exception.ReplenishmentOverrunException;
import com.alexshabanov.txtest.service.model.BalanceChangeEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.concurrent.CyclicBarrier;

/**
 * Runs application.
 */
public final class AppRunner implements Runnable {

    @Autowired
    private BankService bankService;


    private long addUserWithBalance(String username, double balance) {
        final long userId = bankService.registerUser(username);
        bankService.replenish(userId, balance);

        final double newBalance = bankService.getBalance(userId);
        System.out.println(MessageFormat.format(
                "Added user {0}, id={1}, balance = {2}",
                username, userId, newBalance));
        return userId;
    }

    private synchronized void printSync(String message) {
        System.out.println(message);
    }


    private void addBalanceInMultipleThreads(final long uid) {
        final int threadCount = 9;

        // lock for each thread for simultaneous start
        final CyclicBarrier barrier = new CyclicBarrier(threadCount, new Runnable() {
            public void run() {
                printSync("All the threads met await condition");
            }
        });

        // helper class that implements runner
        final class LocalRunner implements Runnable {
            final double amount;

            final int index;

            final int waitMsec;

            LocalRunner(int index, double amount, int waitMsec) {
                this.index = index;
                this.amount = amount;
                this.waitMsec = waitMsec;
            }

            public void run() {
                printSync(MessageFormat.format("T #{0}: entered; amount={1}",
                        index, amount));

                try {
                    Thread.sleep(waitMsec);
                    printSync(MessageFormat.format("T #{0}: start waiting the others, waiting: {1}",
                            index, barrier.getNumberWaiting()));

                    barrier.await();
                } catch (Exception e) {
                    printSync("Unexpected thread exception");
                    throw new UnsupportedOperationException(e);
                }

                bankService.replenish(uid, amount);
                printSync(MessageFormat.format("T #{0}: stopping", index));
            }
        }

        final Thread[] threads = new Thread[threadCount];

        // create & run each thread
        for (int i = 0; i < threadCount; ++i) {
            final double amount = (i + 1) * 10;
            threads[i] = new Thread(new LocalRunner(i, amount, (threadCount - i) * 50));
            threads[i].start();
        }

        // lock released, wait then
        printSync("Main thread: all threads started");

        // wait each thread
        try {
            for (int i = 0; i < threadCount; ++i) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new UnsupportedOperationException("Unexpected interruption", e);
        }

        printSync("Main thread: all threads stopped");
    }

    private void decBalanceInMultipleThreads(final long uid) {
        final int threadCount = 9;

        // lock for each thread for simultaneous start
        final CyclicBarrier barrier = new CyclicBarrier(threadCount, new Runnable() {
            public void run() {
                printSync("All the threads met await condition");
            }
        });

        // helper class that implements runner
        final class LocalRunner implements Runnable {
            final double amount;

            final int index;

            final int waitMsec;

            LocalRunner(int index, double amount, int waitMsec) {
                this.index = index;
                this.amount = amount;
                this.waitMsec = waitMsec;
            }

            public void run() {
                printSync(MessageFormat.format("T #{0}: entered; amount={1}",
                        index, amount));

                try {
                    Thread.sleep(waitMsec);
                    printSync(MessageFormat.format("T #{0}: start waiting the others, waiting: {1}",
                            index, barrier.getNumberWaiting()));

                    barrier.await();
                } catch (Exception e) {
                    printSync("Unexpected thread exception");
                    throw new UnsupportedOperationException(e);
                }

                bankService.withdraw(uid, amount);
                printSync(MessageFormat.format("T #{0}: stopping", index));
            }
        }

        final Thread[] threads = new Thread[threadCount];

        // create & run each thread
        for (int i = 0; i < threadCount; ++i) {
            final double amount = (i + 1) * 10;
            threads[i] = new Thread(new LocalRunner(i, amount, (threadCount - i) * 50));
            threads[i].start();
        }

        // lock released, wait then
        printSync("Main thread: all threads started");

        // wait each thread
        try {
            for (int i = 0; i < threadCount; ++i) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new UnsupportedOperationException("Unexpected interruption", e);
        }

        printSync("Main thread: all threads stopped");
    }

    private void replenishmentOverrunTest(long uid) {
        System.out.println(MessageFormat.format("User # {0} OLD balance={1}", uid, bankService.getBalance(uid)));
        final double tooBigAmount = bankService.getMaxReplenishmentAmount() + 1000;

        try {
            bankService.replenish(uid, tooBigAmount);
        } catch (ReplenishmentOverrunException e) {
            System.out.println("Expected exception: " + e.getMessage());
        }

        System.out.println(MessageFormat.format("User # {0} NEW balance={1}", uid, bankService.getBalance(uid)));
    }

    private void printUsersBalance(long ... uids) {
        for (final long uid : uids) {
            System.out.println(MessageFormat.format("User # {0} balance={1}", uid, bankService.getBalance(uid)));
        }
    }

    private void printUsersBalanceHistory(long ... uids) {
        for (final long uid : uids) {
            System.out.println("--------------------------------------------------------");
            System.out.println(MessageFormat.format("User # {0} balance history: ", uid));

            final Collection<BalanceChangeEntry> entries = bankService.queryBalanceChanges(uid);
            for (final BalanceChangeEntry entry : entries) {
                System.out.println(MessageFormat.format("\t{0}", entry));
            }

            System.out.println();
        }
    }


    /**
     * {@inheritDoc}
     */
    public void run() {
        bankService.setMaxReplenishmentAmount(1000);

        final long alexId = addUserWithBalance("alex", 500);
        final long bobId = addUserWithBalance("bob", 400);
        final long daveId = addUserWithBalance("dave", 500);
        final long cavinId = addUserWithBalance("cavin", 500);

        final long[] allUids = new long[] { alexId, bobId, daveId, cavinId };

        // check balance before
        printUsersBalance(allUids);

        // ----------------------------------------------------------
        // tests

        bankService.withdraw(bobId, 1);

        // tests the transactional nature of the bankService
        replenishmentOverrunTest(alexId);

        addBalanceInMultipleThreads(alexId);
//        addBalanceInMultipleThreads(bobId);
        addBalanceInMultipleThreads(daveId);
        addBalanceInMultipleThreads(cavinId);

        // check balance after
        printUsersBalance(allUids);

        decBalanceInMultipleThreads(alexId);
        decBalanceInMultipleThreads(daveId);
        decBalanceInMultipleThreads(cavinId);


        // check balance after
        printUsersBalance(allUids);

        if (allUids.length > 9) {
            // disable unused methods
            printUsersBalanceHistory(allUids);
        }
    }
}
