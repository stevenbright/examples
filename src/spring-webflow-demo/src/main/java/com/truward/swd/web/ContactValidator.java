package com.truward.swd.web;

import com.truward.swd.domain.Contact;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

@Component
public class ContactValidator {
    public void validate(Contact booking, ValidationContext context) {
            MessageContext messages = context.getMessageContext();
            if (booking.getFirstname() == null || booking.getFirstname().length() == 0) {
                messages.addMessage(new MessageBuilder().error().source("firstname").
                    defaultText("Firstname should not be empty'").build());
            } else if (booking.getLastname() == null || booking.getLastname().length() == 0) {
                messages.addMessage(new MessageBuilder().error().source("lastname").
                    defaultText("Lastname should not be empty'").build());
            }
        }

}
