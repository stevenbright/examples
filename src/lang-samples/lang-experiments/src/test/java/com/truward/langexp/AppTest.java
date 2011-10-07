package com.truward.langexp;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest extends Assert {

//    class MyList<A> {
//        <T> T[] toArray(T[] v) {
//            MyList<T> t = this;
//
//            return null;
//        }
//    }

    class Animal {}

    class Dog extends Animal {}

    class Bird extends Animal {}

    List<Animal> getAnimals() {
        List<Animal> animals = new ArrayList<Animal>();

        animals.add(getDog());
        animals.add(getBird());

        return animals;
    }

    Dog getDog() {
        return null;
    }

    Bird getBird() {
        return null;
    }

    @Test
    public void testApp() {
        assertTrue(true);
    }
}
