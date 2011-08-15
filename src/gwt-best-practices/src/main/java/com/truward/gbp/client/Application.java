package com.truward.gbp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.truward.gbp.client.controllers.LoginController;
import com.truward.gbp.client.services.AppService;
import com.truward.gbp.client.services.AppServiceAsync;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final AppServiceAsync appService = GWT.create(AppService.class);
        final HandlerManager eventBus = new HandlerManager(null);
        final HasWidgets rootPanel = RootPanel.get();
        final LoginController loginController = new LoginController(appService, eventBus);

        // start logging in
        loginController.start(rootPanel);
    }
}
