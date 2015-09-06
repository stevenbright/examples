package com.alexshabanov.sample.springProps.service;

import org.slf4j.LoggerFactory;

public final class LocalFooService implements FooService {

  @Override
  public void bar() {
    LoggerFactory.getLogger(getClass()).info("LOCAL FooService.bar()");
  }
}
