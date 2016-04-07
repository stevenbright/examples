package com.alexshabanov.grpc.hello;

import io.grpc.*;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.AbstractStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static io.grpc.stub.ClientCalls.blockingUnaryCall;

/**
 * @author Alexander Shabanov
 */
public final class HelloClient {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final ManagedChannel channel;
  private final HelloServiceGrpc.HelloServiceBlockingClient blockingStub;

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public HelloClient(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port)
        .usePlaintext(true) // - for debug only
        .build();
    blockingStub = new HelloJsonStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /** Say hello to server. */
  public void greet(String name) {
    log.info("Will try to greet {} ...", name);
    HelloModel.GreetingRequest request = HelloModel.GreetingRequest.newBuilder().setName(name).build();
    HelloModel.GreetingReply response;
    try {
      response = blockingStub.getGreeting(request);
    } catch (StatusRuntimeException e) {
      log.warn("RPC failed: {}", e.getStatus());
      return;
    }
    log.info("Greeting: {}", response.getGreeting());
  }

  public static void main(String[] args) throws InterruptedException {
    HelloClient client = new HelloClient("localhost", 8081);
    try {
      /* Access a service running on the local machine on port 50051 */
      String user = "world";
      if (args.length > 0) {
        user = args[0]; /* Use the arg as the name to greet if provided */
      }
      client.greet(user);

      String user2 = "GRPC client";
      if (args.length > 1) {
        user2 = args[1]; /* Use the arg as the name to greet if provided */
      }
      client.greet(user2);
    } finally {
      client.shutdown();
    }
  }

  //
  // Client impl
  //

  private static final class HelloJsonStub extends AbstractStub<HelloJsonStub>
      implements HelloServiceGrpc.HelloServiceBlockingClient {

    static final MethodDescriptor<HelloModel.GreetingRequest, HelloModel.GreetingReply> METHOD_GET_GREETING =
        MethodDescriptor.create(
            HelloServiceGrpc.METHOD_GET_GREETING.getType(),
            HelloServiceGrpc.METHOD_GET_GREETING.getFullMethodName(),
            ProtoUtils.marshaller(HelloModel.GreetingRequest.getDefaultInstance()),
            ProtoUtils.marshaller(HelloModel.GreetingReply.getDefaultInstance()));

    protected HelloJsonStub(Channel channel) {
      super(channel);
    }

    protected HelloJsonStub(Channel channel, CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected HelloJsonStub build(Channel channel, CallOptions callOptions) {
      return new HelloJsonStub(channel, callOptions);
    }

    @Override
    public HelloModel.GreetingReply getGreeting(HelloModel.GreetingRequest request) {
      return blockingUnaryCall(getChannel(), METHOD_GET_GREETING, getCallOptions(), request);
    }
  }
}
