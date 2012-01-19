package com.alexshabanov.sprj.model;

import java.io.Serializable;

/**
 * Base class for all the domain objects.
 */
public abstract class DomainObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public abstract String toString();
}
