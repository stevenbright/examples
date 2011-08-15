package com.mysite.gwtspringmvc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.mysite.gwtspringmvc.client.services.DataService;
import com.mysite.gwtspringmvc.client.services.DataServiceAsync;
import com.mysite.gwtspringmvc.client.views.MainView;
import com.mysite.gwtspringmvc.shared.SampleBean;

import java.util.List;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final MainView mainView = new MainView();
        final DataServiceAsync service = GWT.create(DataService.class);

        mainView.getQueryButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                service.getBeanFor(mainView.getQueryBox().getText(),
                        new AsyncCallback<List<SampleBean>>() {
                            public void onFailure(Throwable caught) {
                                GWT.log("Error: " + caught);
                            }

                            public void onSuccess(List<SampleBean> result) {
                                mainView.addQueryResults(result);
                            }
                        });
            }
        });

        RootPanel.get().add(mainView);
    }
}
