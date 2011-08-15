package com.mysite.jbossesb.wc.controller;


import com.mysite.jbossesb.wc.model.SendEsbMessageCommand;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public final class EsbMessagingController {

    private static final Logger LOG = LoggerFactory.getLogger(EsbMessagingController.class);

    @RequestMapping("/sendesb.html")
    public String sendEsb(Model model) {
        final SendEsbMessageCommand command = new SendEsbMessageCommand();
        command.setServiceCategory("FirstServiceESB");
        command.setServiceName("SimpleListener");
        command.setMessage("Hello, ESB! # " + System.currentTimeMillis());

        model.addAttribute("command", command);
        return "sendesb";
    }

    @RequestMapping(value = "/sendesb.do", method = RequestMethod.POST)
    public String doSendEsb(@ModelAttribute SendEsbMessageCommand command) {
        try {
            LOG.info("Sending ESB command = {}", command);

            final Message esbMessage = MessageFactory.getInstance().getMessage();
            esbMessage.getBody().add(command.getMessage());
            new ServiceInvoker(command.getServiceCategory(), command.getServiceName()).deliverAsync(esbMessage);

            return "redirect:/sendesb.html?code=0";
        } catch (Exception e) {
            LOG.error("Error when sending ESB", e);
            return "redirect:/sendesb.html?error=1";
        }
    }
}
