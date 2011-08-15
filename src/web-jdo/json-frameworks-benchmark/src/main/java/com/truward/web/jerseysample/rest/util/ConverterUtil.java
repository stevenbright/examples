package com.truward.web.jerseysample.rest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Encapsulates exposure conversion routines.
 */
public final class ConverterUtil {

    // ISO 8601-compatible format
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static Date dateFromString(final String date) throws ParseException {
        return DATE_FORMAT.parse(date);
    }


    public static String dateToString(final Date date) {
        return DATE_FORMAT.format(date);
    }


    /** Hidden ctor */
    private ConverterUtil() {}
}
