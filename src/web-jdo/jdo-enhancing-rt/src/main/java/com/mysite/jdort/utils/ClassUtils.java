package com.mysite.jdort.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utilities for working with classes
 */
public final class ClassUtils {

    public static byte[] loadClassAsByteArray(String className, ClassLoader classLoader) throws IOException {
        final String classNamePath = className.replace('.', '/') + ".class";
        final InputStream is = classLoader.getResourceAsStream(classNamePath);

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final byte[] buffer = new byte[4096];

        for (;;) {
            int bytesRead = is.read(buffer, 0, buffer.length);
            if (bytesRead <= 0) {
                break;
            }

            os.write(buffer, 0, bytesRead);
        }

        is.close();
        return os.toByteArray();
    }

    public static byte[] loadClassAsByteArray(String className) throws IOException {
        return loadClassAsByteArray(className, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Hidden ctor
     */
    private ClassUtils() {}
}
