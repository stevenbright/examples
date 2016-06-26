package com.alexshabanov.sample.usus;

import com.truward.brikar.server.launcher.StandardLauncher;

/**
 * Entry point
 */
public final class UserServiceUsageExampleLauncher {

  public static void main(String[] args) throws Exception {
    try (StandardLauncher launcher = new StandardLauncher("classpath:/ususService/")) {
      launcher
          .setSpringSecurityEnabled(true)
          .setSessionsEnabled(true)
          .setStaticHandlerEnabled(true)
          .start();
    }
  }
}
