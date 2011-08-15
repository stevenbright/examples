package com.mysite.aopsetprop.jpf.aspect;

import com.mysite.aopsetprop.jpf.HandlerRegistration;
import com.mysite.aopsetprop.jpf.NotifyPropertyChanged;
import com.mysite.aopsetprop.jpf.PropertyChangedHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Standard implementation of NotifyPropertyChanged interface.
 */
public class NotifyPropertyChangedBase implements NotifyPropertyChanged {

    private static class HandlerEntry {
        int key;

        PropertyChangedHandler handler;

        private HandlerEntry(int key, PropertyChangedHandler handler) {
            this.key = key;
            this.handler = handler;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HandlerEntry that = (HandlerEntry) o;

            return key == that.key;

        }

        @Override
        public int hashCode() {
            return key;
        }
    }

    private int lastKey = 0;

    private Set<HandlerEntry> handlerEntries = new HashSet<HandlerEntry>();

    public HandlerRegistration addPropertyChangedListener(PropertyChangedHandler handler) {
        if (handler == null) {
            throw new NullPointerException("handler");
        }

        final HandlerEntry entry = new HandlerEntry(++lastKey, handler);
        handlerEntries.add(entry);

        return new HandlerRegistration() {
            public void removeHandler() {
                handlerEntries.remove(entry);
            }
        };
    }

    /**
     * Helper method that "emits" propertyChanged event.
     * @param propertyName Name of the changed property.
     */
    protected void propertyChanged(String propertyName) {
        for (final HandlerEntry entry : handlerEntries) {
            entry.handler.propertyChanged(this, propertyName);
        }
    }
}
