package com.mysite.gde.client.style;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Defines global resources accessed application wide
 */
public interface DesktopStyleBundle extends ClientBundle {
    interface GlobalStyle extends CssResource {
        @ClassName("popup-panel")
        String popupPanel();

        @ClassName("text-box")
        String textBox();

        String button();

        String header();

        @ClassName("checkbox-label")
        String checkboxLabel();
    }

    @Source("global.css")
    GlobalStyle globalStyle();
}
