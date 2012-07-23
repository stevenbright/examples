package com.alexshabanov.springjmsdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.atomic.AtomicInteger;

@Service("jmsMessageListener")
public class JmsMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(JmsMessageListener.class);

    @Inject
    private AtomicInteger counter = null;

    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            int messageCount = message.getIntProperty(JmsProducer.MESSAGE_COUNT);

            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage)message;
                String msg = tm.getText();


                logger.info("Processed message '{}'.  value={}", msg, messageCount);

                counter.incrementAndGet();
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
