package com.alexshabanov.restcomp.exposure;


import com.alexshabanov.restcomp.exposure.mockprovider.MockServiceContextResolver;
import com.alexshabanov.restcomp.exposure.server.service.DemoService;
import com.alexshabanov.restcomp.exposure.shared.DataTraits;
import com.alexshabanov.restcomp.exposure.shared.gson.provider.GsonAwareContextResolver;
import com.alexshabanov.restcomp.exposure.shared.gson.provider.GsonMessageBodyHandler;
import com.alexshabanov.restcomp.exposure.shared.model.NumHolder;
import com.alexshabanov.restcomp.exposure.shared.protobuf.provider.ProtobufMessageBodyHandler;
import com.alexshabanov.restcomp.model.NumHolderProtobuf;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class RestApiTest extends JerseyTest {
    /**
     * Creates custom REST client config which is mandatory since we don't use any JSON providers.
     * @return Jersey Client Config with the required classes to read/write in(out)coming data.
     */
    private static ClientConfig createClientConfig() {
        final ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(GsonMessageBodyHandler.class);
        config.getClasses().add(GsonAwareContextResolver.class);

        config.getClasses().add(ProtobufMessageBodyHandler.class);

        return config;
    }

    private static final String[] SERVER_PACKAGES = new String[] {
            "com.alexshabanov.restcomp.exposure.shared.gson.provider",
            "com.alexshabanov.restcomp.exposure.shared.protobuf.provider",

            "com.alexshabanov.restcomp.exposure.server.provider",

            "com.alexshabanov.restcomp.exposure.mockprovider"
    };

    /**
     * Public ctor
     * @throws com.sun.jersey.test.framework.spi.container.TestContainerException On error
     */
    public RestApiTest() throws TestContainerException {
        super(new WebAppDescriptor.Builder(SERVER_PACKAGES)
                .clientConfig(createClientConfig())
                .contextPath("/")
                .build());
    }


    private DemoService demoService;

    @Before
    public void setupServices() {
        demoService = MockServiceContextResolver.DEMO_SERVICE;
    }



    @Test
    public void testProtobufSum() {
        reset(demoService);
        demoService.addNumber(eq(1));
        expectLastCall().once();
        demoService.addNumber(eq(2));
        expectLastCall().once();
        final int sum = 3;
        expect(demoService.getSum()).andReturn(sum);
        replay(demoService);

        final WebResource resource = resource();

        resource.path("/demo/pb/num")
                .type(DataTraits.PROTOBUF_MEDIATYPE)
                .post(NumHolderProtobuf.NumHolder.newBuilder().setNumber(1).build());
        resource.path("/demo/pb/num")
                .type(DataTraits.PROTOBUF_MEDIATYPE)
                .post(NumHolderProtobuf.NumHolder.newBuilder().setNumber(2).build());

        final int result = resource.path("/demo/pb/sum")
                .type(DataTraits.PROTOBUF_MEDIATYPE)
                .get(NumHolderProtobuf.NumHolder.class).getNumber();
        assertEquals(sum, result);

        verify(demoService);
    }

    @Test
    public void testGetFavoriteNumbers() {
        reset(demoService);
        final Integer[] arr = new Integer[] { 1, 2, 3, 4 };
        expect(demoService.getFavoriteNumbers()).andReturn(Arrays.<Integer>asList(arr));
        replay(demoService);

        final Collection<Integer> received = resource().path("/demo/fav")
                .type(MediaType.APPLICATION_JSON)
                .get(new GenericType<Collection<Integer>>() {});
        assertArrayEquals(arr, received.toArray(new Integer[0]));

        verify(demoService);
    }

    @Test
    public void testSum() {
        reset(demoService);
        demoService.addNumber(eq(1));
        expectLastCall().once();
        demoService.addNumber(eq(2));
        expectLastCall().once();
        final int sum = 3;
        expect(demoService.getSum()).andReturn(sum);
        replay(demoService);

        final WebResource resource = resource();

        resource.path("/demo/num")
                .type(MediaType.APPLICATION_JSON)
                .post(NumHolder.fromNumber(1));
        resource.path("/demo/num")
                .type(MediaType.APPLICATION_JSON)
                .post(NumHolder.fromNumber(2));

        final int result = resource.path("/demo/sum")
                .type(MediaType.APPLICATION_JSON)
                .get(Integer.class);
        assertEquals(sum, result);

        verify(demoService);
    }
}
