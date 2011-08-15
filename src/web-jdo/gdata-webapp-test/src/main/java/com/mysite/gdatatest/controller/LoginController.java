package com.mysite.gdatatest.controller;

import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthParameters;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.ServiceException;
import com.mysite.gdatatest.model.UserSessionContext;
import com.mysite.gdatatest.util.AppTraits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * OAuth Login Controller.
 */
@Controller
public final class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private static final String APP_SCOPE = "https://www.google.com/calendar/feeds/";


    @Resource
    private Map<String, String> appProperties;


    @RequestMapping("/login.html")
    public ModelAndView login() {
        final Map<String, String> model = new HashMap<String, String>();
        model.put("googleClientId", appProperties.get("google.clientId"));
        model.put("googleAppScope", APP_SCOPE);

        return new ModelAndView("public/login", model);
    }

    @RequestMapping("/oauth.do")
    public ModelAndView oauthDo(@RequestParam(required = false) String code,
                                @RequestParam(required = false) String error,
                                HttpSession session) throws IOException, OAuthException, ServiceException {
        LOG.trace("oauthDo: code={}, error={}", code, error);

        final Map<String, String> model = new HashMap<String, String>();

        model.put("error", error);
        model.put("code", code);

        if (code != null) {
            //final String accessToken = GoogleUtil.fetchAccessToken(code, appProperties);


            final OAuthParameters parameters = new OAuthParameters();
            parameters.setOAuthToken(code);
            parameters.setOAuthCallback("http://localhost:9090/gdata-webapp-test/oauth.do");
            parameters.setOAuthConsumerSecret(appProperties.get("google.clientSecret"));
            parameters.setOAuthToken(code);
            //parameters.setOAuthType(OAuthParameters.OAuthType.THREE_LEGGED_OAUTH);
            parameters.setOAuthConsumerKey(appProperties.get("google.clientId"));


            final CalendarService service = new CalendarService(appProperties.get("google.appName"));
            service.setOAuthCredentials(parameters, new OAuthHmacSha1Signer());

            URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/allcalendars/full");
            CalendarFeed resultFeed = service.getFeed(feedUrl, CalendarFeed.class);
            System.out.println("Your calendars:");
            System.out.println();
            for (int i = 0; i < resultFeed.getEntries().size(); i++) {
                CalendarEntry entry = resultFeed.getEntries().get(i);
                System.out.println("\t" + entry.getTitle().getPlainText());
            }

            session.setAttribute(AppTraits.USER_SESSION_CONTEXT, new UserSessionContext(code));
        } else {
            session.setAttribute(AppTraits.USER_SESSION_CONTEXT, null);
        }

        return new ModelAndView("public/oauth", model);
    }
}
