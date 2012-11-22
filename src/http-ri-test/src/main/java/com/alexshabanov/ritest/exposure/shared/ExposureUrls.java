package com.alexshabanov.ritest.exposure.shared;

/**
 * Shared REST API exposure traits.
 */
public final class ExposureUrls {
    private ExposureUrls() {}

    public static final String GET_HELLO = "/hello/{subject}";

    public static final String CREATE_GREETING = "/submit";
}
