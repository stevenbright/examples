package com.truward.swd.web;

import com.truward.swd.domain.Contact;
import com.truward.swd.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes
public final class ContactsController {

    private final Logger log = LoggerFactory.getLogger(ContactsController.class);

    @RequestMapping(value = "/addContact", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contact") Contact contact,
                             BindingResult result) {

        log.info("First Name: " + contact.getFirstname() +
                "Last Name: " + contact.getLastname());

        return "redirect:contacts";
    }

    @RequestMapping(value = "/contacts")
    public ModelAndView showContacts() {
        return new ModelAndView("contact", "command", DataUtil.createContact(0));
    }
}
