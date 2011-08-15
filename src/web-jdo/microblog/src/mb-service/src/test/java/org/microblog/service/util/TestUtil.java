package org.microblog.service.util;

import org.microblog.exposure.model.Chunk;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Encapsulates all the test utilities.
 */
public final class TestUtil {

    /**
     * Helper test method, that compares chunk given with the expected count and values.
     * @param chunk Compared chunk.
     * @param expectedCount Expected count in the chunk.
     * @param expectedValues Expected values returned in the chunk.
     * @param <T> Chunk item type parameter.
     */
    public static <T> void assertChunkEquals(Chunk<T> chunk, int expectedCount, T ... expectedValues) {
        assertEquals(expectedCount, chunk.getCount());
        assertNotNull(chunk.getItems());
        assertArrayEquals(expectedValues, chunk.getItems().toArray());
    }

    public static <T> void assertChunkEquals(Chunk<T> chunk, int expectedCount) {
        assertEquals(expectedCount, chunk.getCount());
        assertNotNull(chunk.getItems());
        assertEquals(0, chunk.getItems().size());
    }



    /** Hidden ctor */
    private TestUtil() {}
}
