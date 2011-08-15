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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Implements processing service.
 */
public final class ProcessingServiceImpl implements ProcessingService, InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingServiceImpl.class);

    private volatile int notificationCounter = 0;

    private volatile int afterNotificationToken = 0;

    private static final int PROCESSING_SLEEP_DELAY = 5000;

    private static final int PROCESSING_JOIN_DELAY = PROCESSING_SLEEP_DELAY * 2;


    private static final int PROCESSING_WAIT_SLEEPING = 100;

    private static final int PROCESSING_WAIT_SLEEPING_ITER = 10;


    @Resource
    private BankOperationDao bankOperationDao;

    @Resource
    private UserDao userDao;


    private static enum State {
        STOP,
        PROCESS,
        RUNNING,
        SLEEPING;
    }

    private final class ProcessingRunner implements Runnable {
        private volatile State state = State.SLEEPING;

        private boolean nextIteration = false;

        public void run() {
            while (state != State.STOP) {
                if (nextIteration) {
                    switch (state) {
                        case PROCESS:
                            state = State.RUNNING;
                            processPendingOperations();
                            state = State.SLEEPING;
                            break;

                        case SLEEPING:
                            // Do nothing - no pending operations are on line
                            break;

                        default:
                            LOG.info("Unprocessed state: " + state);
                    }
                } else {
                    nextIteration = true;
                }

                try {
                    Thread.sleep(PROCESSING_SLEEP_DELAY);
                } catch (InterruptedException e) {
                    // check processing command at once.
                    LOG.trace("Interrupted - execution of the pending operation");
                }
            }
        }
    }

    private final ProcessingRunner processingRunner = new ProcessingRunner();

    private final Thread processingThread = new Thread(processingRunner);

    /**
     * Processes all the pending operations and moves them to the SUCCEED status.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
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

            // wait for sleeping state
            for (int i = 0; i < PROCESSING_WAIT_SLEEPING_ITER; ++i) {
                if (processingRunner.state == State.SLEEPING) {
                    LOG.trace("After operation: token={}, operationId={}, invoking PROCESS state",
                            notificationToken, operationId);

                    processingRunner.state = State.PROCESS;
                    // interrupt in case thread is still sleeping
                    processingThread.interrupt();
                    return;
                }

                guaranteedSleep(PROCESSING_WAIT_SLEEPING);
            }

            LOG.error("After operation: token={}, operationId={}, timeout when waiting for SLEEP",
                    notificationToken, operationId);
            throw new ServiceException("Didn't got to the sleeping state");
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

        processingRunner.state = State.STOP;
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
