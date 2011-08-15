package com.mysite.gwtspringmvc.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.mysite.gwtspringmvc.shared.SampleBean;

import java.util.List;

public class MainView extends Composite {
    @UiField
    Label greetingLabel;

    @UiField
    TextBox queryBox;

    @UiField
    Button queryButton;

    @UiField
    Panel queryResults;
    

    @UiTemplate("MainView.ui.xml")
    static interface ViewUiBinder extends UiBinder<Widget, MainView> {
    }

    private static final ViewUiBinder VIEW_UI_BINDER = GWT.create(ViewUiBinder.class);

    public MainView() {
        initWidget(VIEW_UI_BINDER.createAndBindUi(this));

        greetingLabel.setText("Hello from MainView!");
    }

    public HasClickHandlers getQueryButton() {
        return queryButton;
    }

    public HasText getQueryBox() {
        return queryBox;
    }

    public void addQueryResults(List<SampleBean> beans) {
        queryResults.clear();

        for (final SampleBean bean : beans) {
            final Label label = new Label();

            label.setText("name: " + bean.getName() + ", age: " +
                    bean.getAge() + ", comment: " + bean.getComment());

            queryResults.add(label);
        }
    }
}
