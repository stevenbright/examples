package org.microblog.exposure.testutil.provider;

import org.easymock.EasyMock;
import org.microblog.exposure.server.service.BaseService;
import org.microblog.exposure.server.service.BlogService;
import org.microblog.exposure.server.service.UserService;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Represents context resolver for the exposed services.
 */
@Provider
public final class MockServiceContextResolver implements ContextResolver<BaseService> {

    public static final UserService USER_SERVICE = EasyMock.createMock(UserService.class);

    public static final BlogService BLOG_SERVICE = EasyMock.createMock(BlogService.class);


    /**
     * {@inheritDoc}
     */
    public BaseService getContext(Class<?> type) {
        if (BlogService.class.equals(type)) {
            return BLOG_SERVICE;
        }

        if (UserService.class.equals(type)) {
            return USER_SERVICE;
        }

        return null;
    }
}
