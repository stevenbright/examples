package net.threadtxtest.service;

import net.threadtxtest.service.exception.ServiceException;

import java.math.BigDecimal;

/**
 * Transfer.
 */
public interface BankingService {
    long registerUser(String name) throws ServiceException;

    Long getUserIdByName(String name) throws ServiceException;

    long depositMoney(long userId, BigDecimal amount) throws ServiceException;

    BankOperationStatus getOperationStatus(long operationId) throws ServiceException;

    BigDecimal getBalance(long userId) throws ServiceException;
}
