package com.mysite.jbossesb;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

/**
 * ESB Client Sample.
 */
public final class EsbClientSample implements ClientSample {
    public void apply(String[] args) throws Exception {
        // Setting the ConnectionFactory such that it will use scout
        System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");

        final String serviceCategory = "FirstServiceESB";
        final String serviceName = "SimpleListener";
        final String message;

        if (args.length > 2) {
            message = args[0];
        } else {
            message = "Hello, Sample ESB!";
        }

        System.out.println("Issuing ESB message: " + message);


        final Message esbMessage = MessageFactory.getInstance().getMessage();
        esbMessage.getBody().add(message);

        new ServiceInvoker(serviceCategory, serviceName).deliverAsync(esbMessage);
    }
}
