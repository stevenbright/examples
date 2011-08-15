package com.mysite.gwtspringmvc.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mysite.gwtspringmvc.shared.SampleBean;

import java.util.List;

/**
 * Async aspect of the sample service class
 */
public interface DataServiceAsync {
    void getBeanFor(String query, AsyncCallback<List<SampleBean>> async);
}
