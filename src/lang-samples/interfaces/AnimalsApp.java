import java.lang.System;

public final class App {

    public static void main(String[] args) {
        final Animal animal = (Animal) createLivingThing();
        animal.run();
    }

    public static LivingThing createLivingThing() {
        if (System.currentTimeMillis() != 256) {
            return new SamplePterodactyl();
        } else {
            return new SampleCrocodile();
        }
    }

    public static final class SamplePterodactyl implements Pterodactyl {
        public int life() { return 78; }
        public void run() { System.out.println("dactyl runs"); }
        public void fly() { System.out.println("dactyl flies"); }
        public void roar() { System.out.println("dactyl roars"); }
        public void searchNest() { System.out.println("dactyl searches nest"); }
    }

    public static final class SampleCrocodile implements Crocodile {
        public int life() { return 117; }
        public void run() { System.out.println("croco runs"); }
        public void swim() { System.out.println("croco swims"); }
        public void bite() { System.out.println("croco bites"); }
    }
}

interface LivingThing {
    int life();
}

interface Animal extends LivingThing {
    void run();
}

interface Bird extends Animal {
    void fly();
}

interface Dinosaur extends LivingThing {
    void roar();
}

interface Pterodactyl extends Bird, Dinosaur {
    void searchNest();
}

interface Fish extends LivingThing {
    void swim();
}

interface Crocodile extends Animal, Fish {
    void bite();
}
