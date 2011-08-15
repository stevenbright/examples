package com.truward.gbp.client.controllers;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.truward.gbp.client.events.ErrorEvent;
import com.truward.gbp.client.presenters.LoginPresenter;
import com.truward.gbp.client.services.AppServiceAsync;
import com.truward.gbp.client.views.LoginView;
import com.truward.gbp.client.views.ProgressView;
import com.truward.gbp.shared.service.requests.GetLoginStateRequest;
import com.truward.gbp.shared.service.responses.GotLoginStateResponse;

/**
 * Embraces all the actions related to the login scenario
 */
public class LoginController extends AbstractController {
    private LoginPresenter loginPresenter;

    private LoginPresenter getLoginPresenter() {
        if (loginPresenter == null) {
            loginPresenter = new LoginPresenter(new LoginView(), getEventBus(), getService());
        }
        
        return loginPresenter;
    }

    private final ProgressView progressView = new ProgressView();

    public LoginController(AppServiceAsync service, HandlerManager eventBus) {
        super(service, eventBus);
    }

    public void start(final HasWidgets container) {
        container.clear();
        progressView.setProgressText("Logging in...");
        container.add(progressView.asWidget());
        
        getService().process(new GetLoginStateRequest(), new AsyncCallback<GotLoginStateResponse>() {
            public void onFailure(Throwable throwable) {
                getEventBus().fireEvent(new ErrorEvent(throwable));
            }

            public void onSuccess(GotLoginStateResponse response) {
                progressView.setProgressText(response.isLoggedIn() ? "Logged in!" : "Login required");
                container.clear();

                container.add(getLoginPresenter().asWidget());
            }
        });
    }
}
