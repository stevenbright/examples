package com.truward.uigwt.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Panel;

/**
 * Hosts progress bar
 */
public class ProgressBarView {
    public interface Binder extends UiBinder<Panel, ProgressBarView> {}
    private static Binder binder = GWT.create(Binder.class);


    @UiField DivElement indicatorHolder;

    @UiField DivElement indicator;

    @UiField SpanElement progressValue;


    private final Panel panel;


    public ProgressBarView() {
        panel = binder.createAndBindUi(this);

        indicator.getStyle().setWidth(0, Style.Unit.PX);
    }

    public final Panel getPanel() {
        return panel;
    }

    public final void setProgressValue(double value) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("value");
        }

        final Long percents = Math.round(value * 100);
        indicator.getStyle().setWidth(percents, Style.Unit.PCT);
        
        progressValue.setInnerText(percents.toString());
    }
}
