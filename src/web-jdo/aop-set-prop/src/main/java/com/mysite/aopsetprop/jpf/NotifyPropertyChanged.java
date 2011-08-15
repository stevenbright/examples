package com.mysite.aopsetprop.jpf;

/**
 * Notifies about changing some property
 */
public interface NotifyPropertyChanged {

    /**
     * Adds property changed handler.
     * @param handler Object who will handle property changes.
     * @return Handler registration.
     */
    HandlerRegistration addPropertyChangedListener(PropertyChangedHandler handler);
}
