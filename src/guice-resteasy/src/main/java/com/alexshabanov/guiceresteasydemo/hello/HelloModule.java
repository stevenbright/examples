package com.alexshabanov.guiceresteasydemo.hello;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author Alexander Shabanov
 */
public class HelloModule implements Module {
  @Override
  public void configure(Binder binder) {
    binder.bind(HelloResource.class);
  }
}
