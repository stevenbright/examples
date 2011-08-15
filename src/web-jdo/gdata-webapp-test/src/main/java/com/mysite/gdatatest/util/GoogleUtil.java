package com.mysite.gdatatest.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

/**
 * Google utilities.
 */
public final class GoogleUtil {
    /** Hidden ctor */
    private GoogleUtil() {}

    private static final Logger LOG = LoggerFactory.getLogger(GoogleUtil.class);


    public static String fetchAccessToken(String code, Map<String, String> appProperties) throws IOException {
        // try to promote code
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpPost post = new HttpPost("https://accounts.google.com/o/oauth2/token");

        final HttpEntity entity = new UrlEncodedFormEntity(Arrays.asList(
                new BasicNameValuePair("client_id", appProperties.get("google.clientId")),
                new BasicNameValuePair("client_secret", appProperties.get("google.clientSecret")),
                new BasicNameValuePair("redirect_uri", "http://localhost:9090/gdata-webapp-test/oauth.do"),
                new BasicNameValuePair("grant_type", "authorization_code"),
                new BasicNameValuePair("code", code)));
        post.setEntity(entity);

        final HttpResponse response = httpClient.execute(post);
        LOG.info("Status: {}", response.getStatusLine());

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            if (response.getEntity() != null) {
                //final int contentLength = (int) response.getEntity().getContentLength();
                final StringBuilder stringBuilder = new StringBuilder();
                final String encoding = "UTF-8";

                final InputStream inputStream = response.getEntity().getContent();
                try {
                    final InputStreamReader reader = new InputStreamReader(inputStream, encoding);
                    try {
                        for (;;) {
                            final char[] buffer = new char[1024];
                            final int charsRead = reader.read(buffer);
                            if (charsRead <= 0) {
                                break;
                            }

                            stringBuilder.append(buffer, 0, charsRead);
                        }
                    } finally {
                        reader.close();
                    }
                } finally {
                    inputStream.close();
                }

                final String contentStr = stringBuilder.toString();
                return getAccessTokenValue(contentStr);
            }
        }

        return null;
    }

    private static String getAccessTokenValue(String content) {
        //
        // Search the value of the pair "access_token": "<<ACCESS TOKEN VALUE>>",
        // <<ACCESS TOKEN VALUE>> is returned (or null if there is no such value).

        final String accessTokenKey = "\"access_token\"";
        final int accessTokenPos = content.indexOf(accessTokenKey);
        if (accessTokenPos < 0) {
            return null;
        }

        final int valueStart = content.indexOf('"', accessTokenPos + accessTokenKey.length() + 1);
        if (valueStart < 0) {
            return null;
        }

        final int valueEnd = content.indexOf('"', valueStart + 1);
        if (valueEnd < 0) {
            return null;
        }

        return content.substring(valueStart + 1, valueEnd);
    }
}
