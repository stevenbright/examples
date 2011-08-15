package com.mysite.gde.client.blog.ui;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

public class MainView extends Composite {
    interface Binder extends UiBinder<Panel, MainView> {
    }

    static Binder BINDER = GWT.create(Binder.class);

    public MainView() {
        initWidget(BINDER.createAndBindUi(this));
    }
}
