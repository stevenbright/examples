package com.alexshabanov.restcomp.client;

import com.alexshabanov.restcomp.exposure.shared.DataTraits;
import com.alexshabanov.restcomp.exposure.shared.gson.provider.GsonAwareContextResolver;
import com.alexshabanov.restcomp.exposure.shared.gson.provider.GsonMessageBodyHandler;
import com.alexshabanov.restcomp.exposure.shared.model.NumHolder;
import com.alexshabanov.restcomp.exposure.shared.protobuf.provider.ProtobufMessageBodyHandler;
import com.alexshabanov.restcomp.model.NumHolderProtobuf;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;

/**
 * Hello world!
 */
public class App {
    public static void runMeasurements(boolean startJsonTest, String restEndpoint) throws Exception {
        final ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(GsonMessageBodyHandler.class);
        config.getClasses().add(GsonAwareContextResolver.class);
        config.getClasses().add(ProtobufMessageBodyHandler.class);

        final Client client = Client.create(config);
        final WebResource resource = client.resource(restEndpoint);

        final int MAX_ITERATIONS = 1000;

        final int sum;
        final long total;
        if (startJsonTest) {
            final long begin = System.currentTimeMillis();
            for (int i = 0; i < MAX_ITERATIONS; ++i) {
                resource.path("/demo/num")
                        .type(MediaType.APPLICATION_JSON)
                        .post(NumHolder.fromNumber(i));
            }

            total = System.currentTimeMillis() - begin;

            sum = resource.path("/demo/sum")
                    .type(MediaType.APPLICATION_JSON)
                    .get(Integer.class);
        } else {
            final long begin = System.currentTimeMillis();
            for (int i = 0; i < MAX_ITERATIONS; ++i) {
                resource.path("/demo/pb/num")
                        .type(DataTraits.PROTOBUF_MEDIATYPE)
                        .post(NumHolderProtobuf.NumHolder.newBuilder()
                                .setNumber(i)
                                .build());
            }

            total = System.currentTimeMillis() - begin;

            sum = resource.path("/demo/pb/sum")
                    .type(DataTraits.PROTOBUF_MEDIATYPE)
                    .get(NumHolderProtobuf.NumHolder.class).getNumber();
        }




        System.out.println("sum = " + sum);
        System.out.println("total time " + total + " ms");
    }

    public static void main(String[] args) {
        final boolean startJsonTest;
        if (args.length > 0) {
            startJsonTest = args[0].equals("json");
        } else {
            startJsonTest = false;
        }

        final String restEndpoint;
        if (args.length > 1) {
            restEndpoint = args[1];
        } else {
            restEndpoint = "http://127.0.0.1:9090/demo-web-portal/rest";
        }

        System.out.println("Starting demo " + (startJsonTest ? "JSON" : "PROTOBUF") + " client using endpoint " + restEndpoint);

        try {
            runMeasurements(startJsonTest, restEndpoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
