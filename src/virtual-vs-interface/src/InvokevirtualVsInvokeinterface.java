
public final class InvokevirtualVsInvokeinterface {
    static final int ITERATIONS = 100000000;

    static interface I {
        int foo();
    }

    static class A implements I {
        @Override public int foo() { return 0; }
    }

    static class B extends A {
    }

    static volatile I iv = new B();
    static volatile A av = new B();

    static I inv = new B();
    static A anv = new B();

    public static void main(String[] args) {

        for (int u = 0; u < 4; ++u) {
            {
                final long start = System.nanoTime();
                for (int k = 0; k < ITERATIONS; ++k) {
                    av.foo();
                }
                final long delta = System.nanoTime() - start;
                System.out.println("[ ABT ] delta = " + delta);
            }

            {
                final long start = System.nanoTime();
                for (int k = 0; k < ITERATIONS; ++k) {
                    iv.foo();
                }
                final long delta = System.nanoTime() - start;
                System.out.println("[IFACE] delta = " + delta);
            }
        }

        for (int u = 0; u < 4; ++u) {
            {
                final long start = System.nanoTime();
                for (int k = 0; k < ITERATIONS; ++k) {
                    anv.foo();
                }
                final long delta = System.nanoTime() - start;
                System.out.println("[ ABT ] NV delta = " + delta);
            }

            {
                final long start = System.nanoTime();
                for (int k = 0; k < ITERATIONS; ++k) {
                    inv.foo();
                }
                final long delta = System.nanoTime() - start;
                System.out.println("[IFACE] NV delta = " + delta);
            }
        }
    }
}
