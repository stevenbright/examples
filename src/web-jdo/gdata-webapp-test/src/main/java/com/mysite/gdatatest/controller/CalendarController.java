package com.mysite.gdatatest.controller;

import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.ServiceException;
import com.mysite.gdatatest.util.AppTraits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Controller that incorporates calendar functionality.
 */
@Controller
@SessionAttributes(AppTraits.USER_SESSION_CONTEXT)
public class CalendarController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private Map<String, String> appProperties;

    @RequestMapping("/cal/show.html")
    public String show() throws OAuthException, IOException, ServiceException {
        final String appName = appProperties.get("google.appName");

        final GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();


        final CalendarService service = new CalendarService(appName);
        service.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
        LOG.info("Show calendar. API Version: {}", service.getServiceVersion());



        final URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/allcalendars/full");
        final CalendarFeed resultFeed = service.getFeed(feedUrl, CalendarFeed.class);

        LOG.info("Got {} calendar entry(ies)", resultFeed.getEntries().size());

        return "cal/show";
    }
}
