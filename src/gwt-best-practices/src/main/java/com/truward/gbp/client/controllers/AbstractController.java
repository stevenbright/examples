package com.truward.gbp.client.controllers;

import com.google.gwt.event.shared.HandlerManager;
import com.truward.gbp.client.services.AppServiceAsync;

/**
 * Represents base controller class
 */
public abstract class AbstractController {
    private final AppServiceAsync service;

    private final HandlerManager eventBus;

    /**
     * Default constructor
     * @param service Application service
     * @param eventBus Event bus
     */
    public AbstractController(AppServiceAsync service, HandlerManager eventBus) {
        this.service = service;
        this.eventBus = eventBus;
    }

    /**
     * Gets application service
     * @return Application service
     */
    protected final AppServiceAsync getService() {
        return service;
    }

    public final HandlerManager getEventBus() {
        return eventBus;
    }

    //    public final void setup(HasWidgets container) {
//        container.clear();
//    }
}
