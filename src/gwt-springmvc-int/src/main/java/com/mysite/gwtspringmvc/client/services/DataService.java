package com.mysite.gwtspringmvc.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mysite.gwtspringmvc.shared.SampleBean;

import java.util.List;

/**
 * Sample service
 */
@RemoteServiceRelativePath("data")
public interface DataService extends RemoteService {
    List<SampleBean> getBeanFor(String query);
}
