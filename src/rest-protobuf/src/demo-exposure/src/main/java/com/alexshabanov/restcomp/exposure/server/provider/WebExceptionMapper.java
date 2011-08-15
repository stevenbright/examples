package com.alexshabanov.restcomp.exposure.server.provider;

import com.alexshabanov.restcomp.exposure.shared.model.ErrorDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

/**
 * Maps certain exception thrown from the exposed layer to the appropriate web exception.
 */
@Provider
public final class WebExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(WebExceptionMapper.class);

    private static ErrorDescription getErrorDescription(Exception exception) {
        final ErrorDescription errorDescription = new ErrorDescription();

        // set error message
        errorDescription.setErrorMessage(exception.getMessage());

        // set error code
        if (exception instanceof UnsupportedOperationException) {
            errorDescription.setErrorCode(ErrorDescription.UNSUPPORTED);
        } else {
            errorDescription.setErrorCode(ErrorDescription.UNCLASSIFIED);
        }

        return errorDescription;
    }

    private Response.Status getStatus(Exception exception) {
        if (exception instanceof IllegalArgumentException) {
            return BAD_REQUEST;
        }

//        if (exception instanceof SessionNotFoundException) {
//            return FORBIDDEN;
//        }

        return INTERNAL_SERVER_ERROR;
    }


    /**
     * {@inheritDoc}
     */
    public Response toResponse(Exception exception) {
        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }

        LOG.error("Resource invocation error", exception);

        return Response
                .status(getStatus(exception))
                .entity(getErrorDescription(exception))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
