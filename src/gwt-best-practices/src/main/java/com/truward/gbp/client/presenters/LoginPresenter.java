package com.truward.gbp.client.presenters;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.truward.gbp.client.events.ErrorEvent;
import com.truward.gbp.client.events.LoggedInEvent;
import com.truward.gbp.client.services.AppServiceAsync;
import com.truward.gbp.client.views.BaseView;
import com.truward.gbp.shared.service.ServiceException;
import com.truward.gbp.shared.service.requests.LoginRequest;
import com.truward.gbp.shared.service.responses.LoginResponse;

/**
 * Represents login presenter
 */
public class LoginPresenter {

    public interface View extends BaseView {
        String getLogin();

        String getPassword();

        void setLoginError(String loginError);

        HasClickHandlers getLoginButton();
    }

    private final View view;

    private final HandlerManager eventBus;

    private final AppServiceAsync appService;


    public LoginPresenter(View view, HandlerManager eventBus, AppServiceAsync appService) {
        this.view = view;
        this.eventBus = eventBus;
        this.appService = appService;

        view.getLoginButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doLogin();
            }
        });
    }

    public final Widget asWidget() {
        return view.asWidget();
    }


    private void doLogin() {
        appService.process(new LoginRequest(view.getLogin(), view.getPassword()),
                new AsyncCallback<LoginResponse>() {
                    public void onFailure(Throwable caught) {
                        if (caught instanceof ServiceException) {
                            final ServiceException e = (ServiceException) caught;
                            view.setLoginError(e.getReadableMessage());
                        } else {
                            eventBus.fireEvent(new ErrorEvent(caught));
                        }
                    }

                    public void onSuccess(LoginResponse result) {
                        eventBus.fireEvent(new LoggedInEvent());
                    }
                });
    }
}
