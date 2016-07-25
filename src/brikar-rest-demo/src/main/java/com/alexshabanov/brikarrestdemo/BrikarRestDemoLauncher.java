package com.alexshabanov.brikarrestdemo;

import com.truward.brikar.server.launcher.StandardLauncher;

/**
 * @author Alexander Shabanov
 */
public final class BrikarRestDemoLauncher {

  public static void main(String[] args) throws Exception {
    try (final StandardLauncher launcher = new StandardLauncher("classpath:/brikarRestDemo/")) {
      launcher
          .setRequestIdOperationsEnabled(false) // disable request logging for fair comparison w/guice-resteasy-*
          .start();
    }
  }
}
