package com.mysite.gde.client.blog;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mysite.gde.client.blog.ui.*;
import com.mysite.gde.client.style.DesktopStyleBundle;


public final class Application implements EntryPoint {
    @Override
    public void onModuleLoad() {
        final DesktopStyleBundle styleBundle = GWT.create(DesktopStyleBundle.class);
        styleBundle.globalStyle().ensureInjected();


        /* Hide the loading message */
        final Element loading = Document.get().getElementById("loading");
        loading.getParentElement().removeChild(loading);

        Widget widget;
        if (!GWT.getVersion().contains("ultima")) {
            widget = new MainView();
        } else if (GWT.getVersion().contains("rope")) {
            widget = new LoginView();
        } else if (GWT.getVersion().contains("rope2")) {
            widget = new HeaderView();
        } else if (GWT.getVersion().contains("rope3")) {
            widget = new FooterView();
        } else if (GWT.getVersion().contains("rope4")) {
            widget = new RegistrationView();
        } else {
            widget = new Label();
        }

        RootPanel.get().add(widget);
    }
}
