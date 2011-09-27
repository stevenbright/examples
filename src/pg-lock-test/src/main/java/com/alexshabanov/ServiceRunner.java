package com.alexshabanov;


import com.alexshabanov.service.UserService;
import com.alexshabanov.service.domain.UserAccount;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.concurrent.CyclicBarrier;

public final class ServiceRunner implements Runnable {
    @Resource
    private UserService userService;


    private synchronized void printSync(String message) {
        System.out.println(message);
        System.out.flush();
    }


    private void addBalanceInMultipleThreads(final int uid) {
        final int threadCount = 10;

        // lock for each thread for simultaneous start
        final CyclicBarrier barrier = new CyclicBarrier(threadCount, new Runnable() {
            public void run() {
                printSync("All the threads met await condition");
            }
        });

        // helper class that implements runner
        final class LocalRunner implements Runnable {
            final BigDecimal amount;

            final int index;

            final int waitMsec;

            LocalRunner(int index, BigDecimal amount, int waitMsec) {
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

                userService.addToUserBalance(uid, amount);
                printSync(MessageFormat.format("T #{0}: stopping", index));
            }
        }

        final Thread[] threads = new Thread[threadCount];

        // create & run each thread
        for (int i = 0; i < threadCount; ++i) {
            final BigDecimal amount = BigDecimal.valueOf((i + 1) * 10);
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

    private void checkBalanceScenario1() {
        userService.addUserAccount("alex", BigDecimal.valueOf(100));

        // modify balance
        {
            final UserAccount account = userService.getUserAccount("alex");
            printSync(MessageFormat.format("account[{0}, {1}, {2}]",
                    account.getId(), account.getName(), account.getBalance()));

            addBalanceInMultipleThreads(account.getId());
        }

        // recheck balance
        {
            printSync("AFTER-CHECK");

            final UserAccount account = userService.getUserAccount("alex");
            printSync(MessageFormat.format("account[{0}, {1}, {2}]",
                    account.getId(), account.getName(), account.getBalance()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        System.out.println("User service: " + userService);

        userService.deleteAllUsers();

        try {
            checkBalanceScenario1();
        } finally {
            userService.deleteAllUsers();
        }
    }
}
