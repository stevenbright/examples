package com.mysite.jbossesb.wc.controller;

import com.mysite.jbossesb.wc.model.SendJmsMessageCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Controller
public final class JmsMessagingController implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(JmsMessagingController.class);

    private static final class JmsSender {
        private final QueueConnection queueConnection;
        private final QueueSession queueSession;
        private final Queue queue;

        public JmsSender() throws JMSException, NamingException {
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

            LOG.info("Connection Started");
        }

        public void stop() throws JMSException {
            queueConnection.stop();
            queueSession.close();
            queueConnection.close();
        }

        public void sendMessage(String message) throws JMSException {
            final QueueSender send = queueSession.createSender(queue);
            final ObjectMessage tm = queueSession.createObjectMessage(message);
            send.send(tm);
            send.close();
        }
    }

    private JmsSender jmsSender;

    private JmsSender getJmsSender() throws NamingException, JMSException {
        if (jmsSender == null) {
            jmsSender = new JmsSender();
        }
        return jmsSender;
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() throws Exception {
        if (jmsSender != null) {
            jmsSender.stop();
        }
    }



    @RequestMapping("/sendjms.html")
    public String sendJms(Model model) {
        final SendJmsMessageCommand command = new SendJmsMessageCommand();
        command.setMessage("Hello, JMS! # " + System.currentTimeMillis());

        model.addAttribute("command", command);
        return "sendjms";
    }

    @RequestMapping(value = "/sendjms.do", method = RequestMethod.POST)
    public String doSendJms(@ModelAttribute SendJmsMessageCommand command) {
        try {
            LOG.info("Sending JMS command = {}", command);

            getJmsSender().sendMessage(command.getMessage());

            return "redirect:/sendjms.html?code=0";
        } catch (Exception e) {
            LOG.error("Error when sending ESB", e);
            return "redirect:/sendjms.html?error=1";
        }
    }
}
