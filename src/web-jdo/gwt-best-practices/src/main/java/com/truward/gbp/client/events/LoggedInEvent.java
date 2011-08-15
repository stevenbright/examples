package com.truward.gbp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents login event
 */
public final class LoggedInEvent extends GwtEvent<LoggedInEvent.Handler> {
    /**
     * Represents handler to this event
     */
    public interface Handler extends EventHandler {
        void onLoggedIn(LoggedInEvent event);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();


    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onLoggedIn(this);
    }
}
