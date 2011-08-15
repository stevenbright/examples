package com.truward.uigwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.truward.uigwt.client.views.ProgressBarView;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final HasWidgets layoutPanel = RootPanel.get("layout");
        //final Label widget = new Label("Hello, there!");
        final ProgressBarView progressBarView = new ProgressBarView();
        final Widget widget = progressBarView.getPanel();


        // show timer progress
        final double progressInc = 0.03;
        final Timer timer = new Timer() {
            double progress = 0.0;

            @Override
            public void run() {
               progress += progressInc;
                if (progress > 1.0) {
                    progress = 1.0;
                }

                progressBarView.setProgressValue(progress);

                if (progress == 1.0) {
                    this.cancel();
                }
            }
        };
        timer.scheduleRepeating(200);

        layoutPanel.add(widget);
    }
}
