package com.alexshabanov.restcomp.exposure.shared.protobuf.provider;

import com.alexshabanov.restcomp.exposure.shared.DataTraits;
import com.alexshabanov.restcomp.model.NumHolderProtobuf;
import com.google.protobuf.GeneratedMessage;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * Encapsulates protobuf support for custom serializing/deserializing objects.
 * TODO: [ProtobufMessageBodyHandler] This is the reusable class.
 */
@Provider
@Produces(DataTraits.PROTOBUF_MEDIATYPE)
@Consumes(DataTraits.PROTOBUF_MEDIATYPE)
public class ProtobufMessageBodyHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

    // message body reader implementation

    /**
     * {@inheritDoc}
     */
    public boolean isReadable(Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
        if (type.equals(NumHolderProtobuf.NumHolder.class)) {
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public Object readFrom(Class<Object> type, Type genericType, java.lang.annotation.Annotation[] annotations,
                           MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {

        if (type.equals(NumHolderProtobuf.NumHolder.class)) {
            final Object result = NumHolderProtobuf.NumHolder.parseFrom(entityStream);
            return result;
        }

        throw new IOException("Unreadable type " + type);
    }


    // message body writer implementation

    /**
     * {@inheritDoc}
     */
    public boolean isWriteable(Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
        if (GeneratedMessage.class.isAssignableFrom(type)) {
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public long getSize(Object object, Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
        final GeneratedMessage generatedMessage = (GeneratedMessage) object;
        if (!generatedMessage.isInitialized()) {
            return -1;
        }

        return generatedMessage.getSerializedSize();
    }

    /**
     * {@inheritDoc}
     */
    public void writeTo(Object object, Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        final GeneratedMessage generatedMessage = (GeneratedMessage) object;
        if (!generatedMessage.isInitialized()) {
            throw new IOException("object " + object + " is not initialized");
        }

        generatedMessage.writeTo(entityStream);
    }
}
