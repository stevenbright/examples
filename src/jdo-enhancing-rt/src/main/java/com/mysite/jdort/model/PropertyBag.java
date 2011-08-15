package com.mysite.jdort.model;

/**
 * Reloaded JDO classes CAN NOT be converted to self in the code
 * that has not been reloaded, so property bags is used
 */
public interface PropertyBag {
    String getStringProperty(String propertyName);
}
