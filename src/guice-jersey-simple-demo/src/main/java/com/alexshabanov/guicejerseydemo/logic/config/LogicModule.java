package com.alexshabanov.guicejerseydemo.logic.config;

import com.alexshabanov.guicejerseydemo.logic.aspect.LoggerAspect;
import com.alexshabanov.guicejerseydemo.logic.service.UserService;
import com.alexshabanov.guicejerseydemo.logic.service.support.DefaultUserService;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * Business logic configuration.
 */
public final class LogicModule extends AbstractModule {
  @Override
  protected void configure() {
    //bind(App.class);

    // services
    bind(UserService.class).to(DefaultUserService.class);

    bindInterceptor(Matchers.inPackage(DefaultUserService.class.getPackage()),
        Matchers.any(),
        new LoggerAspect());
  }
}
