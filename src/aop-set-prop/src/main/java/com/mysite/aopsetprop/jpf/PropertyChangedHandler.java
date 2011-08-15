package com.mysite.aopsetprop.jpf;

/**
 * Listens for property changing
 */
public interface PropertyChangedHandler {
    void propertyChanged(Object sender, String propertyName);
}
