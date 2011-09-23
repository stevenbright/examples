package com.alexshabanov.util;

import com.alexshabanov.service.domain.UserAccount;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Domain-specific utility.
 */
public final class DomainUtil {
    private DomainUtil() {}

    public static void assertAccountEquals(int expectedId, String expectedName, BigDecimal expectedBalance,
                                           UserAccount actual) {
        assertNotNull(actual);

        assertEquals(expectedId, actual.getId());
        assertEquals(expectedName, actual.getName());
        assertEquals(expectedBalance, actual.getBalance());
        assertNotNull(actual.getCreated());
    }
}
