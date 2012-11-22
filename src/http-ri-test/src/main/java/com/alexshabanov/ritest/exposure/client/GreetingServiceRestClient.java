package com.alexshabanov.ritest.exposure.client;

import com.alexshabanov.ritest.exposure.shared.GreetingRequest;
import com.alexshabanov.ritest.exposure.shared.InlineString;
import com.alexshabanov.ritest.model.WarmGreeting;
import com.alexshabanov.ritest.service.GreetingService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;

import javax.inject.Inject;

import static com.alexshabanov.ritest.exposure.shared.ExposureUrls.CREATE_GREETING;
import static com.alexshabanov.ritest.exposure.shared.ExposureUrls.GET_HELLO;

/**
 * Rest client implementation of {@link GreetingService}.
 */
@Service
public final class GreetingServiceRestClient implements GreetingService {
    private final RestOperations restOperations;
    private final String baseUrl;

    private String url(String suffix) {
        String separator = "/";
        if (baseUrl.endsWith(separator) || suffix.startsWith(separator)) {
            separator = "";
        }

        return baseUrl + separator + suffix;
    }

    @Inject
    public GreetingServiceRestClient(String baseUrl, RestOperations restOperations) {
        Assert.hasLength(baseUrl);
        Assert.notNull(restOperations);

        this.baseUrl = baseUrl;
        this.restOperations = restOperations;
    }

    @Override
    public String getHello(String subject) {
        return restOperations.getForObject(url(GET_HELLO), InlineString.class, subject).getValue();
    }

    @Override
    public WarmGreeting createGreeting(String origin, int warmLevel, double sincerity) {
        final GreetingRequest request = new GreetingRequest();
        request.setOrigin(origin);
        request.setSincerity(sincerity);
        request.setWarmLevel(warmLevel);
        return restOperations.postForObject(url(CREATE_GREETING), request, WarmGreeting.class);
    }
}
