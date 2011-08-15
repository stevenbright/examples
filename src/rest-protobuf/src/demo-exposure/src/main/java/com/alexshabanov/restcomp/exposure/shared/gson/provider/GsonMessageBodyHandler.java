package com.alexshabanov.restcomp.exposure.shared.gson.provider;

import com.alexshabanov.restcomp.exposure.shared.gson.GsonAware;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Encapsulates GSON support for custom serializing/deserializing objects.
 * TODO: [GsonMessageBodyHandler] This is the reusable class.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class GsonMessageBodyHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

    private static final String CHARSET = "UTF-8";

    private Gson gson;

    @Context
    public Providers providers;



    private Gson getGson() {
        if (gson == null) {
            final GsonBuilder gsonBuilder = new GsonBuilder();

            // additional step: configure gson builder if needed
            // the configuration info is fetched from the particular instance of ContextResolver<GsonAware> class.
            final ContextResolver<GsonAware> configContextResolver = providers.getContextResolver(GsonAware.class, null);
            if (configContextResolver != null) {
                final GsonAware gsonAware = configContextResolver.getContext(GsonAware.class);
                if (gsonAware != null) {
                    gsonAware.setGsonBuilder(gsonBuilder);
                }
            }

            gson = gsonBuilder.create();
        }

        return gson;
    }



    // message body reader implementation

    /**
     * {@inheritDoc}
     */
    public boolean isReadable(Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
        // all the types are supported
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public Object readFrom(Class<Object> type, Type genericType, java.lang.annotation.Annotation[] annotations,
                           MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {
        // all the aspects of the system shall encode their content in the UTF-8 encoding.
        final InputStreamReader streamReader = new InputStreamReader(entityStream, CHARSET);
        try {
            final Type jsonType;
            if (Collection.class.isAssignableFrom(type) && genericType != null) {
                jsonType = genericType;
            } else {
                jsonType = type;
            }

            return getGson().fromJson(streamReader, jsonType);
        } finally {
            streamReader.close();
        }
    }


    // message body writer implementation

    /**
     * {@inheritDoc}
     */
    public boolean isWriteable(Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public long getSize(Object object, Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    public void writeTo(Object object, Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        final OutputStreamWriter writer = new OutputStreamWriter(entityStream, CHARSET);
        try {
            getGson().toJson(object, writer);
        } finally {
            writer.flush();
        }
    }
}
