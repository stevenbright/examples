package com.mysite.mvcwsdemo.ws;

import com.mysite.mvcwsdemo.service.HumanResourceService;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HolidayEndpoint extends AbstractJDomPayloadEndpoint {
    private final Logger log = Logger.getLogger(HolidayEndpoint.class);

    private final HumanResourceService humanResourceService;

    private final XPath startDateExpression;

    private final XPath endDateExpression;

    private final XPath nameExpression;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HolidayEndpoint(HumanResourceService humanResourceService) throws JDOMException {
        this.humanResourceService = humanResourceService;

        final Namespace namespace = Namespace.getNamespace("hr", "http://mycompany.com/hr/schemas");

        startDateExpression = XPath.newInstance("//hr:StartDate");
        startDateExpression.addNamespace(namespace);

        endDateExpression = XPath.newInstance("//hr:EndDate");
        endDateExpression.addNamespace(namespace);

        nameExpression = XPath.newInstance("concat(//hr:FirstName,' ',//hr:LastName)");
        nameExpression.addNamespace(namespace);
    }

    @Override
    protected Element invokeInternal(Element holidayRequest) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("HolidayEndpoint::invokeInternal");
        }

        final Date startDate = dateFormat.parse(startDateExpression.valueOf(holidayRequest));
        final Date endDate = dateFormat.parse(endDateExpression.valueOf(holidayRequest));
        final String name = nameExpression.valueOf(holidayRequest);

        humanResourceService.bookHoliday(startDate, endDate, name);

        return null;
    }
}
