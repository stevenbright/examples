package com.alexshabanov.springintdemo.service.impl.apns;

import com.alexshabanov.springintdemo.service.impl.apns.model.ApnsCompositePayload;
import com.alexshabanov.springintdemo.service.impl.apns.model.ApnsPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApnsManager {

    private final Logger log = LoggerFactory.getLogger(ApnsManager.class);

    private static final int DEFAULT_MAX_MESSAGES = 2;

    @Value("${app.apns.maxUnreleasedMessages}")
    private int maxMessages = DEFAULT_MAX_MESSAGES;

    private volatile long correlationId = 0;

    @Aggregator
    public ApnsCompositePayload aggregate(List<ApnsPayload> payloads) {
        ++correlationId;
        final List<String> messages = new ArrayList<String>(payloads.size());

        for (final ApnsPayload p : payloads) {
            messages.add("ApnsMsg->" + p.getDeviceToken() + "#" + p.hashCode());
        }

        return new ApnsCompositePayload(messages);
    }

    @ReleaseStrategy
    public boolean canRelease(List<ApnsPayload> messages) {
        log.info("Can-release messages {}", messages);
        return messages.size() >= maxMessages;
    }

    @CorrelationStrategy
    public long fetchCorrelationId(ApnsPayload payload) {
        return correlationId;
    }
}
