package net.threadtxtest.service.internal.impl;

import net.threadtxtest.service.BankOperationStatus;
import net.threadtxtest.service.BankingService;
import net.threadtxtest.service.exception.ServiceException;
import net.threadtxtest.service.internal.dao.BankOperationDao;
import net.threadtxtest.service.internal.dao.UserDao;
import net.threadtxtest.service.internal.domain.BankOperation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Implementation of banking service.
 */
@Transactional
public final class BankingServiceImpl implements BankingService {
    @Resource
    private UserDao userDao;

    @Resource
    private BankOperationDao bankOperationDao;

    @Resource
    private ProcessingService processingService;

    /**
     * {@inheritDoc}
     */
    public long registerUser(String name) throws ServiceException {
        if (userDao.findUserByName(name) != null) {
            throw new ServiceException("User " + name + " already exists");
        }

        return userDao.addUser(name);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Long getUserIdByName(String name) throws ServiceException {
        return userDao.findUserByName(name);
    }

    /**
     * {@inheritDoc}
     */
    public long depositMoney(long userId, BigDecimal amount) throws ServiceException {
        final int notificationToken = processingService.notifyBeforeOperation();
        final long operationId = bankOperationDao.addOperation(userId, amount);
        processingService.notifyAfterOperation(notificationToken, operationId);

        return operationId;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public BankOperationStatus getOperationStatus(long operationId) throws ServiceException {
        final BankOperation operation = bankOperationDao.getOperation(operationId);
        return operation == null ? null : operation.getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public BigDecimal getBalance(long userId) throws ServiceException {
        return userDao.getBalance(userId);
    }
}
