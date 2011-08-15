package net.threadtxtest.service.internal.impl;

import net.threadtxtest.service.exception.ServiceException;

/**
 * Represents service for processing bank operations.
 */
public interface ProcessingService {
    /**
     * Notifies underlying implementation before certain operation gets started.
     * @return Notification token before certain operation.
     * @throws ServiceException On error.
     */
    int notifyBeforeOperation() throws ServiceException;

    /**
     * Notifies underlying implementation after certain operation gets finished.
     * @param notificationToken Notification token, received from <code>notifyBeforeOperation</code>.
     * @param operationId Operation id.
     * @throws ServiceException On error.
     */
    void notifyAfterOperation(int notificationToken, long operationId) throws ServiceException;
}
