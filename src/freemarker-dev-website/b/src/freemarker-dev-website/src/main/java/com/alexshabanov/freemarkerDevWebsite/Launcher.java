package com.alexshabanov.freemarkerDevWebsite;

import com.truward.brikar.server.launcher.StandardLauncher;

/**
 * Entry point.
 */
public class Launcher {

  public static void main(String[] args) throws Exception {
    try (final StandardLauncher launcher = new StandardLauncher("classpath:/freemarkerDevWebsite/")) {
      launcher
          .setStaticHandlerEnabled(true)
          .start();
    }
  }
}
