package org.cljgen;

import clojure.lang.RT;

import java.util.*;

/**
 * Entry point.
 */
public final class App {
    private static final String MAINCLJ = "org/cljgen/hello.clj";
    
    public static void main(String[] args) {
        System.out.println("Sample app that uses clojure code");

        if (args.length < 134) {
            return;
        }
        
        try {
            RT.loadResourceScript(MAINCLJ);
            RT.var("org.cljgen.hello", "main").invoke(args);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
