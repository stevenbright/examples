package com.truward.gbp.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.truward.gbp.client.services.AppService;
import com.truward.gbp.server.DummyDao;
import com.truward.gbp.server.processors.GetLoginStateProcessor;
import com.truward.gbp.server.processors.LoginProcessor;
import com.truward.gbp.server.processors.Processor;
import com.truward.gbp.shared.service.ServiceException;
import com.truward.gbp.shared.service.ServiceRequest;
import com.truward.gbp.shared.service.ServiceResponse;
import com.truward.gbp.shared.service.requests.GetLoginStateRequest;
import com.truward.gbp.shared.service.requests.LoginRequest;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an implementation of the application services
 */
public class AppServiceImpl extends RemoteServiceServlet implements AppService {
    private final static String USERNAME_ATTR = "username";

    private final DummyDao dao = new DummyDao();

    private final Map<Class, Object> processors = new HashMap<Class, Object>();


    public AppServiceImpl() {
        processors.put(LoginRequest.class, new LoginProcessor());
        processors.put(GetLoginStateRequest.class, new GetLoginStateProcessor());
        // ..


    }


    public <T extends ServiceResponse> T process(ServiceRequest<T> request) throws ServiceException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // just ignore it
        }

        final Processor<T> processor = (Processor<T>) processors.get(request.getClass());
        final HttpSession session = getThreadLocalRequest().getSession();

        final Processor.Context context = new Processor.Context() {

            public DummyDao getDao() {
                return dao;
            }

            public String getCurrentUser() {
                return (String) session.getAttribute(USERNAME_ATTR);
            }

            public void setCurrentUser(String username) {
                session.setAttribute(USERNAME_ATTR, username);
            }
        };


        final T response = processor.processRequest(request, context);
        return response;
    }
}