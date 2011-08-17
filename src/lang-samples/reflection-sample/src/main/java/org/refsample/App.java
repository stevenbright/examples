package org.refsample;

import org.refsample.service.Bird;
import org.refsample.service.Robot;
import org.refsample.service.impl.Eagle;
import org.refsample.service.impl.RoboticFalcon;

import java.util.Random;

/**
 * Entry point.
 */
public final class App {
    private static final Random BIRD_RANDOM = new Random(19L);

    private static final int BIRD_COUNT = 5;

    private static void dumpClass(Class<?> klass) {
        System.out.println("Exploring " + klass.getName() + ", interfaces:");
        for (final Class<?> iface : klass.getInterfaces()) {
            System.out.println("\t" + iface.getName());
        }
    }

    private static Bird createRandomBird(int index) {
        if (BIRD_RANDOM.nextBoolean()) {
            return new RoboticFalcon(index);
        } else {
            return new Eagle(index);
        }
    }

    private static void handleBird(Bird bird) {
        bird.fly();
        bird.run();
        if (bird instanceof Robot) {
            Robot robot = (Robot) bird;
            robot.moveTo(BIRD_RANDOM.nextInt(100), BIRD_RANDOM.nextInt(100));
        }
    }

    public static void main(String[] args) {
        System.out.println("Birds app!");

        final Bird[] birds = new Bird[BIRD_COUNT];

        dumpClass(Eagle.class);
        dumpClass(RoboticFalcon.class);

        for (int i = 0; i < BIRD_COUNT; ++i) {
            birds[i] = createRandomBird(i);
        }

        for (int i = 0; i < BIRD_COUNT; ++i) {
            handleBird(birds[i]);
        }
    }
}
