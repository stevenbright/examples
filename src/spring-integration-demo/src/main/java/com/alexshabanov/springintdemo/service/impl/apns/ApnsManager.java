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

/**
 * Takes care of aggregation, release and correlation of the incoming message.
 */
@Service
public class ApnsManager {

    private final Logger log = LoggerFactory.getLogger(ApnsManager.class);

    private static final int DEFAULT_MAX_UNRELEASED_MESSAGES = 2;

    @Value("${app.apns.maxUnreleasedMessages}")
    private int maxUnreleasedMessages = DEFAULT_MAX_UNRELEASED_MESSAGES;

    // we must increment correlation ID each time the messages gets aggregated into the single chunk.
    // the aggregator specifics guarantees there will be no
    // TODO: check whether the synchronization is required and what it should be
    private volatile long correlationId = 1L;

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
    public boolean canRelease(List<ApnsPayload> unreleasedMessages) {
        log.info("Can-release messages {}", unreleasedMessages);
        return unreleasedMessages.size() >= maxUnreleasedMessages;
    }

    @CorrelationStrategy
    public long fetchCorrelationId(ApnsPayload payload) {
        return correlationId;
    }
}
