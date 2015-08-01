package com.alexshabanov.sample.usus;

import com.truward.brikar.server.launcher.StandardLauncher;

/**
 * Entry point
 */
public final class Launcher extends StandardLauncher {
  public static void main(String[] args) throws Exception {
    new Launcher()
      .setDefaultDirPrefix("classpath:/ususService/")
      .start(args);
  }

    @Override
  protected boolean isSpringSecurityEnabled() {
    return true;
  }
}
