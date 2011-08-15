package com.mysite.gdatatest;

import com.google.gdata.client.authn.oauth.*;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.util.ServiceException;

import java.io.IOException;

/**
 * Runs OAuth1 scenario.
 */
public final class OAuthClientScenarioRunner {

    public static void run(CalendarServiceCallback callback) throws IOException, OAuthException, ServiceException {
        final GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();

        final String consumerKey = System.getProperty("google.consumerKey");
        final String consumerSecret = System.getProperty("google.consumerSecret");

        if (consumerKey == null) {
            throw new UnsupportedOperationException("Auth scenario is not supported when consumer key is missing");
        }

        if (consumerSecret == null) {
            throw new UnsupportedOperationException("Auth scenario is not supported when consumer secret is missing");
        }

        // step [1]: authenticate

        // Set your OAuth Consumer Key (which you can register at
        // https://www.google.com/accounts/ManageDomains).
        oauthParameters.setOAuthConsumerKey(consumerKey);

        // Initialize the OAuth Signer.  2-Legged OAuth must use HMAC-SHA1, which
        // uses the OAuth Consumer Secret to sign the request.  The OAuth Consumer
        // Secret can be obtained at https://www.google.com/accounts/ManageDomains.
        oauthParameters.setOAuthConsumerSecret(consumerSecret);
        final OAuthSigner signer = new OAuthHmacSha1Signer();

        // Finally create a new GoogleOAuthHelperObject.  This is the object you
        // will use for all OAuth-related interaction.
        GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(signer);

        oauthParameters.setScope("https://www.google.com/calendar/feeds/");

        oauthHelper.getUnauthorizedRequestToken(oauthParameters);

        final String authorizationRequestUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
        System.out.println(authorizationRequestUrl);
        System.out.println("Please visit the URL above to authorize your OAuth "
                + "request token.  Once that is complete, press any key to "
                + "continue...");
        System.in.read();

        // step [2]: fetch access token
        String token = oauthHelper.getAccessToken(oauthParameters);
        System.out.println("OAuth Access Token: " + token);
        System.out.println();

        // step [3]: create service and operate on it
        final CalendarService service = new CalendarService("isv-alex-calendar");
        service.setOAuthCredentials(oauthParameters, signer);

        try {
            callback.accept(service);
        } finally {
            // step [4]: revoke access
            oauthHelper.revokeToken(oauthParameters);
        }
    }

    /** Hidden ctor */
    private OAuthClientScenarioRunner() {}
}
