package net.threadtxtest.service.internal.dao;

import net.threadtxtest.service.BankOperationStatus;
import net.threadtxtest.service.internal.domain.BankOperation;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Represents bank operation DAO.
 */
public interface BankOperationDao {
    long addOperation(long userId, BigDecimal amount) throws DataAccessException;

    BankOperation getOperation(long operationId) throws DataAccessException;

    void updateOperationStatus(long operationId, BankOperationStatus status) throws DataAccessException;

    Collection<Long> getPendingOperations() throws DataAccessException;
}
