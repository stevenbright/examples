package net.threadtxtest.service;

import net.threadtxtest.service.exception.ServiceException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Represents banking service.
 */
public interface BankingService {
    /**
     * Registers new user.
     *
     * @param name Unique user name.
     * @return Unique user ID.
     * @throws ServiceException On error.
     */
    long registerUser(String name) throws ServiceException;

    /**
     * Gets user by it's unique ID.
     *
     * @param name Unique user name.
     * @return User ID or null if such user is not found.
     * @throws ServiceException On error.
     */
    Long getUserIdByName(String name) throws ServiceException;

    /**
     * Initiates money deposit on the user's account.
     *
     * @param userId Unique user ID.
     * @param amount Amount of money to be deposited on the user's account.
     * @return Operation ID.
     * @throws ServiceException On error.
     */
    long depositMoney(long userId, BigDecimal amount) throws ServiceException;

    /**
     * Gets operation status.
     *
     * @param operationId Unique operation ID.
     * @return Operation status.
     * @throws ServiceException On error.
     */
    BankOperationStatus getOperationStatus(long operationId) throws ServiceException;

    /**
     * Gets balance of the user's account.
     *
     * @param userId Unique user ID.
     * @return Balance on the user's account.
     * @throws ServiceException On error.
     */
    BigDecimal getBalance(long userId) throws ServiceException;
}
