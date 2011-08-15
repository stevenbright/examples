package com.truward.gbp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents login event
 */
public final class ErrorEvent extends GwtEvent<ErrorEvent.Handler> {
    /**
     * Represents handler to this event
     */
    public interface Handler extends EventHandler {
        void onError(ErrorEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private Throwable cause;

    /**
     * Default constructor
     */
    public ErrorEvent() {
    }

    /**
     * Creates error event with the associated cause
     * @param cause Cause of the error
     */
    public ErrorEvent(Throwable cause) {
        this.cause = cause;
    }

    
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onError(this);
    }
}
