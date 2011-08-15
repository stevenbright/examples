package com.mysite.gde.client.blog.ui;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public final class RegistrationView extends Composite {
    interface Binder extends UiBinder<HTMLPanel, RegistrationView> {
    }

    static Binder BINDER = GWT.create(Binder.class);

    public RegistrationView() {
        initWidget(BINDER.createAndBindUi(this));
    }
}
