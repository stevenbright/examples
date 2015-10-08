package com.alexshabanov.guicejerseydemo.logic.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public final class LoggerAspect implements MethodInterceptor {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    boolean succeeded = false;
    try {
      log.info("Invoking method {} with args {}",
          methodInvocation.getMethod(),
          Arrays.asList(methodInvocation.getArguments()));

      final Object returnValue = methodInvocation.proceed();
      succeeded = true;
      log.info("Method {} returned {}", methodInvocation.getMethod(), returnValue);

      return returnValue;
    } finally {
      if (!succeeded) {
        log.info("Method {} thrown an exception", methodInvocation.getMethod());
      }
    }
  }
}
