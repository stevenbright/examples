package com.alexshabanov.coleq;

import java.util.*;

/**
 * Entry point.
 */
public final class App {

    public static void testCollectionEquals() {
        final Collection<Integer> c1 = Arrays.asList(5, 4, 1, 2, 3);

        final Collection[] collections = new Collection[] {
                c1,
                new ArrayList<Integer>(c1),
                new LinkedList<Integer>(c1),
                new HashSet<Integer>(c1),
                new TreeSet<Integer>(c1),
                new Vector<Integer>(c1)
        };


        for (int i = 0; i < collections.length; ++i) {
            final Collection srcCol = collections[i];
            final String srcName = srcCol.getClass().getSimpleName();
            System.out.println("Test equals for " + srcName + ":");

            for (int j = 0; j < collections.length; ++j) {
                if (i == j) {
                    continue;
                }

                final Collection dstCol = collections[j];
                final boolean isEquals = srcCol.equals(dstCol);
                System.out.println("\t#" + srcName + ".equals(#" +
                        dstCol.getClass().getSimpleName() +
                        ") = " + isEquals);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Test equals behavior for JDK collections");
        testCollectionEquals();
    }
}
