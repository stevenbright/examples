package com.alexshabanov.springintdemo.service.impl.apns;

import com.alexshabanov.springintdemo.service.impl.apns.model.ApnsCompositePayload;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.stereotype.Service;

@Service
public class ApnsCompositeMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        final ApnsCompositePayload payload = (ApnsCompositePayload) message.getPayload();
        System.out.println("Sending composite payload: " + payload);
    }
}
