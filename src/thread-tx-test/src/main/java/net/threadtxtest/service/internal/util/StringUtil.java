package net.threadtxtest.service.internal.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Encapsulates helpers for string operations.
 */
public final class StringUtil {


    private static final int MAX_COLLECTION_ELEMENT = 4;

    /**
     * Stringifies an object given to the given string builder.
     * @param value Value to be stringified.
     * @param appendable Destination appendable sequence.
     * @throws java.io.IOException On error.
     */
    public static void stringify(Object value, Appendable appendable) throws IOException {

        // null value
        if (value == null) {
            appendable.append("null");
            return;
        }

        final Class valueClass = value.getClass();

        // array
        if (valueClass.isArray()) {
            final int count = Array.getLength(value);

            appendable.append("Array#");
            appendable.append(String.valueOf(count));
            appendable.append("[");

            for (int i = 0; i < count; ++i) {
                if (i > 0) {
                    appendable.append(", ");
                }

                if (i > MAX_COLLECTION_ELEMENT) {
                    appendable.append("...");
                    break;
                }

                stringify(Array.get(value, i), appendable);
            }
            appendable.append(']');
            return;
        }

        // iterable
        if (Iterable.class.isAssignableFrom(valueClass)) {
            appendable.append(valueClass.getSimpleName());
            appendable.append('#');
            if (Collection.class.isAssignableFrom(valueClass)) {
                appendable.append(String.valueOf(((Collection) value).size()));
            }
            appendable.append('[');

            final Iterable values = (Iterable) value;
            int i = 0;

            for (final Object element : values) {
                if (i > 0) {
                    appendable.append(", ");
                }

                if (i > MAX_COLLECTION_ELEMENT) {
                    appendable.append("...");
                    break;
                }

                stringify(element, appendable);
                ++i;
            }
            appendable.append(']');
            return;
        }

        // probably atomic, use toString
        appendable.append(value.toString());
    }

    /**
     * Stringifies an object given and returns it's readable representation.
     * @param value Value to be stringified.
     * @return Stringified value.
     * @throws java.io.IOException On error.
     */
    public static String stringify(Object value) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        stringify(value, stringBuilder);
        return stringBuilder.toString();
    }

    /** Hidden ctor */
    private StringUtil() {}
}