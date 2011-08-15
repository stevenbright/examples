package com.mysite.gwtmod.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.mysite.generic.traits.Constants;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final String msg = "Constant values NUM=" + Constants.NUM + ", PATTERN='" + Constants.PATTERN + "'.";
        //final String msg = "Hi!";
        final Label label = new Label(msg);
        RootPanel.get().add(label);
    }
}
