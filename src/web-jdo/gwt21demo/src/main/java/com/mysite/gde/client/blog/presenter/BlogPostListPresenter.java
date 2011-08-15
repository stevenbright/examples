package com.mysite.gde.client.blog.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

public class BlogPostListPresenter implements Presenter {

    public interface View {
        HasClickHandlers getAddButton();
        void setData(List<String> data);
        int getClickedRow(ClickEvent event);
        
        Widget asWidget();
    }
}
