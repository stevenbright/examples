package com.mysite.gde.client.blog.ui;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

public final class FooterView extends Composite {
    interface Binder extends UiBinder<Panel, FooterView> {
    }

    static Binder BINDER = GWT.create(Binder.class);

    public FooterView() {
        initWidget(BINDER.createAndBindUi(this));
    }
}
