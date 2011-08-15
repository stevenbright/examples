package com.mysite.jbossesb;


import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;


public class HelloJmsListenerAction extends AbstractActionLifecycle {
    protected ConfigTree _config;

    public HelloJmsListenerAction(ConfigTree config) {
        this._config = config;
    }

    public Message displayMessage(Message message) throws Exception {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

        if (_config != null) {
            System.out.println("config name = " + _config.getName());
        }

        System.out.println("Hello Body: " + message.getBody().get());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

        return message;
    }
}