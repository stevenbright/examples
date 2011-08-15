package com.alexshabanov.restcomp.exposure.server.service;

import com.alexshabanov.restcomp.exposure.server.exception.ServiceException;

import java.util.Collection;

/**
 * Represents demo service.
 */
public interface DemoService extends ExposedService {
    Collection<Integer> getFavoriteNumbers() throws ServiceException;

    void addNumber(int number);

    int getSum();
}
