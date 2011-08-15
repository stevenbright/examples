package com.truward.gbp.client.controllers;

import com.google.gwt.event.shared.HandlerManager;
import com.truward.gbp.client.services.AppServiceAsync;

/**
 * Takes over the blog access
 */
public class BlogController extends AbstractController {
    public BlogController(AppServiceAsync service, HandlerManager eventBus) {
        super(service, eventBus);
    }
}
