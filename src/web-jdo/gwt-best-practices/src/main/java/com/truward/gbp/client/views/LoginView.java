package com.truward.gbp.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.truward.gbp.client.presenters.LoginPresenter;

/**
 * Represents login view
 */
public class LoginView implements LoginPresenter.View {
    public interface Binder extends UiBinder<FlowPanel, LoginView> {
    }

    @UiField
    TextBox loginBox;
    @UiField
    PasswordTextBox passwordBox;
    @UiField
    Label errorLabel;
    @UiField
    Button loginButton;

    private final Panel rootPanel;


    public LoginView() {
        final Binder binder = GWT.create(Binder.class);
        rootPanel = binder.createAndBindUi(this);
    }

    public String getLogin() {
        return loginBox.getText();
    }

    public String getPassword() {
        return passwordBox.getText();
    }

    public void setLoginError(String loginError) {
        errorLabel.setText(loginError);
    }

    public HasClickHandlers getLoginButton() {
        return loginButton;
    }

    public Widget asWidget() {
        return rootPanel;
    }
}
