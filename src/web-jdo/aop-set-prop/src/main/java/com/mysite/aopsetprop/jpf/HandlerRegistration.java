package com.mysite.aopsetprop.jpf;

/**
 * Registration returned from a call to
 * HandlerManager.add##Handler
 */
public interface HandlerRegistration {
    /**
     * Removes the given handler from its manager.
     */
    void removeHandler();
}
