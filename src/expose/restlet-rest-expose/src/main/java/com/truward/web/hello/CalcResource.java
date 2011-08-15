package com.truward.web.hello;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Rest server calculator resource.
 */
public class CalcResource extends ServerResource {

    @Get("json")
    public String getResource(Integer a, Integer b) {
//        final Form form = getRequest().getResourceRef().getQueryAsForm();
//        final Parameter aParam = form.getFirst("a");
//        final Parameter bParam = form.getFirst("b");
//
//        final int a1 = Integer.parseInt(aParam.getValue());
//        final int b = Integer.parseInt(bParam.getValue());
//
//        final int c = a + b;

        //final Form f = getRequest().getEntityAsForm();
        final Representation r = getRequest().getEntity();
        //final String va = .getFirstValue("a");

        final int c= 77;

        return String.valueOf(c);
    }
}
