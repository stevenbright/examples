package com.alexshabanov.springintdemo.service.impl.apns.model;

import java.util.List;

public class ApnsCompositePayload {
    private final List<String> payload;

    public ApnsCompositePayload(List<String> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ApnsCompositePayload");
        sb.append("{payload=").append(payload);
        sb.append('}');
        return sb.toString();
    }
}
