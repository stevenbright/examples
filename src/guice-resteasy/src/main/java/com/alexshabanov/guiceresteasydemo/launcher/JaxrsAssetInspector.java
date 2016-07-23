package com.alexshabanov.guiceresteasydemo.launcher;

import java.util.Arrays;

/**
 * Returns a list of resources classes, registered in an application.
 */
public interface JaxrsAssetInspector {
  static JaxrsAssetInspector of(Class... classes) {
    return () -> Arrays.asList(classes);
  }

  Iterable<Class<?>> getResourceClasses();
}
