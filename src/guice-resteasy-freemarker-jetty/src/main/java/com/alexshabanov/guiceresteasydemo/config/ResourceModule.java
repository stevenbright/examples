package com.alexshabanov.guiceresteasydemo.config;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Resource module - enumeration of all the JAX RS resources.
 *
 * @author Alexander Shabanov
 */
public final class ResourceModule implements Module {

  @Override
  public void configure(Binder binder) {
  }
}
