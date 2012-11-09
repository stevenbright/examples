import java.security.SecureRandom;
import java.util.Random;

public final class Simple {
    private static final int ITERATIONS = 1000000;
    private static final int CHECKS = 4;

    private static int checkIfaces(Random random, IThree orig) {
        int counter = 0;
        for (int i = 0; i < ITERATIONS; ++i) {
            IThree ii = random.nextLong() == -2 ? new IfaceImpl2() : orig;
            counter += ii.foo(i);
        }
        return counter;
    }

    private static int checkAbt(Random random, AThree orig) {
        int counter = 0;
        for (int i = 0; i < ITERATIONS; ++i) {
            AThree ai = random.nextLong() == -2 ? new AbtImpl2() : orig;
            counter += ai.foo(i);
        }
        return counter;
    }

    public static void main(String[] args) {
        final IThree ii1 = new IfaceImpl1();
        final AThree ai1 = new AbtImpl1();
        final Random random = new SecureRandom();

        for (int i = 0; i < CHECKS; ++i) {
            final long start = System.nanoTime();
            final int result = checkIfaces(random, ii1);
            final long delta = System.nanoTime() - start;
            System.out.println("[IFACE] result = " + result + ", delta = " + delta);
        }

        for (int i = 0; i < CHECKS; ++i) {
            final long start = System.nanoTime();
            final int result = checkAbt(random, ai1);
            final long delta = System.nanoTime() - start;
            System.out.println("[ ABT ] result = " + result + ", delta = " + delta);
        }
    }
}

interface IOne {
    int m11();
    int m12();
    int m13();
    int m14();
    int m15(int x, int y);
    int m16();
}

interface ITwo {
    int m21();
    int m22();
    int m23();
    int m24(int x);
}

interface IThree {
    int m31();
    int m32();
    int foo(int x);
}

abstract class AOne {
    abstract int m11();
    abstract int m12();
    abstract int m13();
    abstract int m14();
    abstract int m15(int x, int y);
    abstract int m16();
}

abstract class ATwo extends AOne {
    abstract int m21();
    abstract int m22();
    abstract int m23();
    abstract int m24(int x);
}

abstract class AThree extends ATwo {
    abstract int m31();
    abstract int m32();
    abstract int foo(int x);
}

final class IfaceImpl1 implements IOne, ITwo, IThree {
    @Override public int m11() { return 1; }
    @Override public int m12() { return 1; }
    @Override public int m13() { return 1; }
    @Override public int m14() { return 1; }
    @Override public int m15(int x, int y) { return 1; }
    @Override public int m16() { return 1; }
    @Override public int m31() { return 1; }
    @Override public int m32() { return 1; }
    @Override public int foo(int x) { return x + 1; }
    @Override public int m21() { return 1; }
    @Override public int m22() { return 1; }
    @Override public int m23() { return 1; }
    @Override public int m24(int x) { return 1; }
}


final class IfaceImpl2 implements IOne, ITwo, IThree {
    @Override public int m11() { return 2; }
    @Override public int m12() { return 2; }
    @Override public int m13() { return 2; }
    @Override public int m14() { return 2; }
    @Override public int m15(int x, int y) { return 2; }
    @Override public int m16() { return 2; }
    @Override public int m31() { return 2; }
    @Override public int m32() { return 2; }
    @Override public int foo(int x) { return x + 2; }
    @Override public int m21() { return 2; }
    @Override public int m22() { return 2; }
    @Override public int m23() { return 2; }
    @Override public int m24(int x) { return 2; }
}

final class AbtImpl1 extends AThree {
    @Override public int m11() { return 1; }
    @Override public int m12() { return 1; }
    @Override public int m13() { return 1; }
    @Override public int m14() { return 1; }
    @Override public int m15(int x, int y) { return 1; }
    @Override public int m16() { return 1; }
    @Override public int m31() { return 1; }
    @Override public int m32() { return 1; }
    @Override public int foo(int x) { return x + 1; }
    @Override public int m21() { return 1; }
    @Override public int m22() { return 1; }
    @Override public int m23() { return 1; }
    @Override public int m24(int x) { return 1; }
}

final class AbtImpl2 extends AThree {
    @Override public int m11() { return 2; }
    @Override public int m12() { return 2; }
    @Override public int m13() { return 2; }
    @Override public int m14() { return 2; }
    @Override public int m15(int x, int y) { return 2; }
    @Override public int m16() { return 2; }
    @Override public int m31() { return 2; }
    @Override public int m32() { return 2; }
    @Override public int foo(int x) { return x + 2; }
    @Override public int m21() { return 2; }
    @Override public int m22() { return 2; }
    @Override public int m23() { return 2; }
    @Override public int m24(int x) { return 2; }
}
