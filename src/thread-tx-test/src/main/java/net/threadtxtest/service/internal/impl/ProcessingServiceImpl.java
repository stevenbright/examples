package net.threadtxtest.service.internal.impl;

import net.threadtxtest.service.BankOperationStatus;
import net.threadtxtest.service.exception.ServiceException;
import net.threadtxtest.service.internal.dao.BankOperationDao;
import net.threadtxtest.service.internal.dao.UserDao;
import net.threadtxtest.service.internal.domain.BankOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Implements processing service.
 */
public final class ProcessingServiceImpl implements ProcessingService, InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingServiceImpl.class);

    private volatile int notificationCounter = 0;

    private volatile int afterNotificationToken = 0;

    private static final int PROCESSING_SLEEP_DELAY = 1000;

    private static final int PROCESSING_JOIN_DELAY = PROCESSING_SLEEP_DELAY * 2;


    private static final int PROCESSING_WAIT_SLEEPING = 100;

    private static final int PROCESSING_WAIT_SLEEPING_ITER = 10;


    @Resource
    private BankOperationDao bankOperationDao;

    @Resource
    private UserDao userDao;



    private final class ProcessingRunner implements Runnable {
        private final BlockingQueue<Boolean> commands = new ArrayBlockingQueue<Boolean>(100);

        public void run() {
            for (;;) {
                try {
                    final Boolean command = commands.take();

                    if (!command) {
                        // do not wait, exit is requested
                        return;
                    }

                    Thread.sleep(PROCESSING_WAIT_SLEEPING);
                } catch (InterruptedException e) {
                    LOG.trace("Processing thread interrupted, now exiting");
                    Thread.currentThread().interrupt();
                    return;
                }

                processPendingOperations();
            }
        }
    }

    private final ProcessingRunner processingRunner = new ProcessingRunner();

    private final Thread processingThread = new Thread(processingRunner);

    /**
     * Processes all the pending operations and moves them to the SUCCEED status.
     */
    @Transactional
    public void processPendingOperations() {
        for (final Long operationId : bankOperationDao.getPendingOperations()) {
            final BankOperation operation = bankOperationDao.getOperation(operationId);
            assert operation.getStatus() == BankOperationStatus.PENDING;

            userDao.modifyBalance(operation.getUserId(), operation.getAmount());

            bankOperationDao.updateOperationStatus(operationId, BankOperationStatus.SUCCEEDED);
        }
    }

    private static void guaranteedSleep(int millis) throws ServiceException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new ServiceException("This thread is not expected to be interrupted", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public synchronized int notifyBeforeOperation() throws ServiceException {
        while (afterNotificationToken < notificationCounter) {
            guaranteedSleep(PROCESSING_WAIT_SLEEPING);
        }

        final int notificationToken = notificationCounter++;
        LOG.trace("Before operation: token={}", notificationToken);
        return notificationToken;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void notifyAfterOperation(int notificationToken, long operationId) throws ServiceException {
        try {
            LOG.trace("After operation: token={}, operationId={}", notificationToken, operationId);

            if (!processingThread.isAlive()) {
                LOG.error("Attempting to process in the dead thread");
                return;
            }

            processingRunner.commands.offer(Boolean.TRUE);
        } finally {
            // mark that this operation gets completed
            afterNotificationToken = notificationToken + 1;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() throws Exception {
        LOG.trace("stopping processing thread...");
        if (!processingThread.isAlive()) {
            LOG.error("Attempting to interrupt dead thread");
            return;
        }

        // stop it
        processingRunner.commands.offer(Boolean.FALSE);
        processingThread.interrupt();

        processingThread.join(PROCESSING_JOIN_DELAY);
        LOG.trace("processing thread stopped");
    }

    /**
     * {@inheritDoc}
     */
    public void afterPropertiesSet() throws Exception {
        if (processingThread.isAlive()) {
            throw new BeanInitializationException("Attempting to initialize twice");
        }

        processingThread.start();
    }
}
