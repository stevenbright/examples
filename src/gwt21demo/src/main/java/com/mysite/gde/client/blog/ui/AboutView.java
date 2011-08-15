package com.mysite.gde.client.blog.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class AboutView extends Composite {
    interface Binder extends UiBinder<HTMLPanel, AboutView> {
    }

    static Binder BINDER = GWT.create(Binder.class);

    public AboutView() {
        initWidget(BINDER.createAndBindUi(this));
    }
}
