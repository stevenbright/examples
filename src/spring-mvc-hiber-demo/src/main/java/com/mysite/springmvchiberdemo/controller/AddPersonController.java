package com.mysite.springmvchiberdemo.controller;

import com.mysite.springmvchiberdemo.model.Person;
import com.mysite.springmvchiberdemo.service.SampleService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public class AddPersonController extends SimpleFormController {
    private SampleService sampleService;

    private int personIndex = 1;

    public void setSampleService(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        final Person person = new Person();

        person.setName("name" + personIndex);
        person.setSubscribedToPosts(false);
        person.setStatus("status" + personIndex);
        person.setAge(1);
        
        ++personIndex;

        return person;
    }

    @Override
    protected ModelAndView onSubmit(Object command) throws Exception {
        final Person person = (Person) command;
        sampleService.save(person);

        // we can redirect to failure page as well (e.g. if person does not pass validation)
        return new ModelAndView(new RedirectView(getSuccessView()));
    }
}