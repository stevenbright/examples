package com.alexshabanov.sample.springProps.service;

import org.slf4j.LoggerFactory;

public final class RemoteFooService implements FooService {
  private final String endpoint;

  public RemoteFooService(String endpoint) {
    this.endpoint = endpoint;
  }

  @Override
  public void bar() {
    LoggerFactory.getLogger(getClass()).info("REMOTE FooService.bar() - using endpoint=" + endpoint);
  }
}
