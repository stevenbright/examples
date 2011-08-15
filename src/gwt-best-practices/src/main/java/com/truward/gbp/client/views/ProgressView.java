package com.truward.gbp.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * Represents progress indicator enclosed to the ui control
 */
public class ProgressView {
    public interface Binder extends UiBinder<FlowPanel, ProgressView> {
    }

    @UiField
    Label progressLabel;

    private final Panel rootPanel;

    public ProgressView() {
        final Binder binder = GWT.create(Binder.class);
        rootPanel = binder.createAndBindUi(this);
    }

    public void setProgressText(String text) {
        progressLabel.setText(text);
    }

    public Widget asWidget() {
        return rootPanel;
    }
}
