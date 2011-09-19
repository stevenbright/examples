package org.aspm;

import clojure.lang.RT;

/**
 * Entry point.
 */
public final class App {
    private static final String MAINCLJ = "org/aspm/bridge.clj";

    public static void main(String[] args) {
        System.out.println("Sample app that uses clojure code");

        try {
            RT.loadResourceScript(MAINCLJ);
            RT.var("org.aspm.bridge", "main").invoke(args);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
