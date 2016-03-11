package com.alexshabanov.grpc.hello;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.net.URL;

/**
 * @author Alexander Shabanov
 */
public final class HelloServer {
  private final int port;
  private final Server server;

//  public HelloServer(int port) throws IOException {
//    this(port, RouteGuideUtil.getDefaultFeaturesFile());
//  }
//
//  /** Create a RouteGuide server listening on {@code port} using {@code featureFile} database. */
//  public HelloServer(int port, URL featureFile) throws IOException {
//    this(ServerBuilder.forPort(port), port, RouteGuideUtil.parseFeatures(featureFile));
//  }

  /** Create a RouteGuide server using serverBuilder as a base and features as data. */
  public HelloServer(ServerBuilder<?> serverBuilder, int port) {
    this.port = port;
    this.server = null;
//    server = serverBuilder.addService(RouteGuideGrpc.bindService(new RouteGuideService(features)))
//        .build();
  }
}
