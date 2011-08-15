package com.alexshabanov.memlock;

import com.alexshabanov.memlock.model.Bar;
import com.alexshabanov.memlock.model.Foo;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Random;

/**
 * Entry point.
 */
public final class App {
    public static final int ARR_SIZE = 1000000;

    private static void printMemUsage(String checkpoint) {
        System.out.println(MessageFormat.format("Mem usage at {0}: total={1}, free={2}",
                checkpoint, Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory()));
    }

    private static void testBarAlloc() throws IOException {
        printMemUsage("enter testBarAlloc");

        final Bar[] barChunk = new Bar[ARR_SIZE];

        final long before = System.currentTimeMillis();

        for (int j = 0; j < ARR_SIZE; ++j) {
            final Bar bar = new Bar();
            bar.setA(-j);
            bar.setB(1 + j);
            barChunk[j] = bar;
        }

        final long total = System.currentTimeMillis() - before;
        System.out.println(MessageFormat.format("Bar alloc done; total time: {0}", total));
        printMemUsage("bar alloc iteration");
        System.out.println("Press key to continue");

        System.in.read();

        // get random element
        {
            final Random random = new SecureRandom();
            final Bar randBar = barChunk[random.nextInt(ARR_SIZE)];
            System.out.println("RandBar = " + randBar);
        }
    }

    private static void testFooAlloc() throws IOException {
        printMemUsage("enter testFooAlloc");

        final Foo[] fooChunk = new Foo[ARR_SIZE];

        final long before = System.currentTimeMillis();

        for (int j = 0; j < ARR_SIZE; ++j) {
            final Foo foo = new Foo();
            foo.setA(-j);
            foo.setB(1 + j);
            fooChunk[j] = foo;
        }

        final long total = System.currentTimeMillis() - before;
        System.out.println(MessageFormat.format("Foo alloc done; total time: {0}", total));
        printMemUsage("foo alloc iteration");
        System.out.println("Press key to continue");

        System.in.read();

        // get random element
        {
            final Random random = new SecureRandom();
            final Foo randFoo = fooChunk[random.nextInt(ARR_SIZE)];
            System.out.println("randFoo = " + randFoo);
        }
    }

    public static void main(String[] args) {
        System.out.println("Measure time to alloc");

        try {
            testBarAlloc();
            //testFooAlloc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
