package com.truward.uigwt.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Panel;

public final class SimpleFormView {
    public interface Binder extends UiBinder<Panel, SimpleFormView> {}
    private static Binder binder = GWT.create(Binder.class);

    @UiField
    ParagraphElement textHolder;

    private final Panel panel;

    public Panel getPanel() {
        return panel;
    }


    public SimpleFormView() {
        panel = binder.createAndBindUi(this);

        textHolder.setTitle("Simple Form");
    }
    
    public void setText(String text, int n) {
        textHolder.setInnerText(text + " - " + n);
    }
}
