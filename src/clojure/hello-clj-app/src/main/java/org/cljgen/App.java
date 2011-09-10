package org.cljgen;

import clojure.lang.RT;

/**
 * Entry point.
 */
public final class App {
    private static final String MAINCLJ = "org/cljgen/hello.clj";

    public static void main(String[] args) {
        System.out.println("Sample app that uses clojure code");

        try {
            RT.loadResourceScript(MAINCLJ);
            RT.var("org.cljgen.hello", "main").invoke(args);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
