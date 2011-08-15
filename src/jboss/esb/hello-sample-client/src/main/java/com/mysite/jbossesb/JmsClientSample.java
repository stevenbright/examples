package com.mysite.jbossesb;


import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class JmsClientSample implements ClientSample {

    public static final class SendJMSMessage {
        private QueueConnection queueConnection;
        private QueueSession queueSession;
        private Queue queue;


        private void setupConnection() throws JMSException, NamingException {
            final Properties properties = new Properties();
            properties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            properties.put(Context.URL_PKG_PREFIXES,
                    "org.jboss.naming:org.jnp.interfaces");
            properties.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1099");
            InitialContext initialContext = new InitialContext(properties);

            final Object tmp = initialContext.lookup("ConnectionFactory");
            final QueueConnectionFactory connectionFactory = (QueueConnectionFactory) tmp;
            queueConnection = connectionFactory.createQueueConnection();
            queue = (Queue) initialContext.lookup("queue/quickstart_helloworld_Request_gw");
            queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            queueConnection.start();
            System.out.println("Connection Started");
        }

        private void stop() throws JMSException {
            queueConnection.stop();
            queueSession.close();
            queueConnection.close();
        }

        private void sendAMessage(String msg) throws JMSException {
            final QueueSender send = queueSession.createSender(queue);
            final ObjectMessage tm = queueSession.createObjectMessage(msg);
            send.send(tm);
            send.close();
        }


        public void send(String message) throws Exception {
            final SendJMSMessage sm = new SendJMSMessage();
            sm.setupConnection();
            sm.sendAMessage(message);
            sm.stop();
        }

    }

    public void apply(String[] args) throws Exception {
        final String message;

        if (args.length > 1) {
            message = args[1];
        } else {
            message = "Hello, Sample JMS!";
        }

        System.out.println("Issuing JMS message: " + message);

        final SendJMSMessage sender = new SendJMSMessage();
        sender.send(message);
    }
}
