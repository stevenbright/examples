package com.alexshabanov.restcomp.exposure.shared;

/**
 * Encapsulates data traits.
 */
public final class DataTraits {

    /**
     * SimpleDateFormat-compliant pattern of the date in ISO 8601-compliant format.
     * This is used when writing date format to string.
     */
    public final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Media type tag for all the messages with protobuf-encoded body.
     */
    public final static String PROTOBUF_MEDIATYPE = "application/x-protobuf";
    //public final static MediaType PROTOBUF_MEDIATYPE = new MediaType("application", "x-protobuf");


    /** Hidden ctor */
    private DataTraits() {}
}
