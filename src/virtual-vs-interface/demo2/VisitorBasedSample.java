import java.lang.IllegalStateException;
import java.lang.String;
import java.lang.System;
import java.lang.Thread;
import java.security.SecureRandom;
import java.util.Random;

public final class VisitorBasedSample {
    static final int ITERATIONS = 1000000;

    static final Random random = new SecureRandom();

    static <T> void permutate(T[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            final int pos = random.nextInt(arr.length);
            T temp = arr[i];
            arr[i] = arr[pos];
            arr[pos] = temp;
        }
    }

    static void testIfc() {
        Ifc.Node[] nodes = new Ifc.Node[ITERATIONS];
        Ifc.Visitor[] visitors = new Ifc.Visitor[ITERATIONS];
        long counter = 0;

        for (int i = 0; i < ITERATIONS; ++i) {
            switch (i % 4) {
                case 0:
                    nodes[i] = System.currentTimeMillis() != 1 ? new Ifc.ExprImpl() : new Ifc.ExprImpl_Stub();
                    break;
                case 1:
                    nodes[i] = System.currentTimeMillis() != 1 ? new Ifc.StmtImpl() : new Ifc.StmtImpl_Stub();
                    break;
                case 2:
                    nodes[i] = System.currentTimeMillis() != 1 ? new Ifc.UnitImpl() : new Ifc.UnitImpl_Stub();
                    break;
                case 3:
                    nodes[i] = System.currentTimeMillis() != 1 ? new Ifc.KlasImpl() : new Ifc.KlasImpl_Stub();
                    break;
                default:
                    throw new IllegalStateException("Should never happen");
            }
        }
        for (int i = 0; i < ITERATIONS; ++i) {
            visitors[i] = i % 2 == 0 ? new Ifc.PosVisitor() : new Ifc.NegVisitor();
        }
        permutate(nodes);
        permutate(visitors);

        long delta = System.nanoTime();
        long accum = 0;
        for (int i = 0; i < ITERATIONS; ++i) {
            accum += nodes[i].apply(visitors[i]);
        }
        delta = System.nanoTime() - delta;
        System.out.println("[Interface-based] " + "delta = " + delta + " ns, accum = " + accum);
    }

    static void testAbc() {
        Abc.Node[] nodes = new Abc.Node[ITERATIONS];
        Abc.Visitor[] visitors = new Abc.Visitor[ITERATIONS];
        long counter = 0;

        for (int i = 0; i < ITERATIONS; ++i) {
            switch (i % 4) {
                case 0:
                    nodes[i] = new Abc.Expr();
                    break;
                case 1:
                    nodes[i] = new Abc.Stmt();
                    break;
                case 2:
                    nodes[i] = new Abc.Unit();
                    break;
                case 3:
                    nodes[i] = new Abc.Klas();
                    break;
                default:
                    throw new IllegalStateException("Should never happen");
            }
        }
        for (int i = 0; i < ITERATIONS; ++i) {
            visitors[i] = i % 2 == 0 ? new Abc.PosVisitor() : new Abc.NegVisitor();
        }
        permutate(nodes);
        permutate(visitors);

        long delta = System.nanoTime();
        long accum = 0;
        for (int i = 0; i < ITERATIONS; ++i) {
            accum += nodes[i].apply(visitors[i]);
        }
        delta = System.nanoTime() - delta;
        System.out.println("[  Class  -based] " + "delta = " + delta + " ns, accum = " + accum);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 4; ++i) {
            testIfc();
            testAbc();
        }
    }
}

final class Ifc {
    interface Node {
        long apply(Visitor v);
    }

    interface Expr extends Node {
        long getFoo();
    }

    interface Stmt extends Node {
        long getBar();
    }

    interface Unit extends Node {
        long getBaz();
    }

    interface Klas extends Node {
        long getRal();
    }

    interface Visitor {
        long visitExpr(Expr n);
        long visitStmt(Stmt n);
        long visitUnit(Unit n);
        long visitKlas(Klas n);
    }

    // implementations

    static class PosVisitor implements Visitor {
        public long visitExpr(Expr n) { return n.getFoo(); }
        public long visitStmt(Stmt n) { return n.getBar(); }
        public long visitUnit(Unit n) { return n.getBaz(); }
        public long visitKlas(Klas n) { return n.getRal(); }
    }

    static class NegVisitor implements Visitor {
        public long visitExpr(Expr n) { return -n.getFoo(); }
        public long visitStmt(Stmt n) { return -n.getBar(); }
        public long visitUnit(Unit n) { return -n.getBaz(); }
        public long visitKlas(Klas n) { return -n.getRal(); }
    }

    static class ExprImpl_Stub implements Expr {
        public long apply(Visitor v) {
            return v.visitExpr(this);
        }

        public long getFoo() {
            System.out.println("stub????");
            return System.nanoTime() + 129341L;
        }
    }

    static class StmtImpl_Stub implements Stmt {
        public long apply(Visitor v) {
            return v.visitStmt(this);
        }

        public long getBar() {
            System.out.println("stub????");
            return new Random().nextInt();
        }
    }

    static class UnitImpl_Stub implements Unit {
        public long apply(Visitor v) {
            return v.visitUnit(this);
        }

        public long getBaz() {
            System.out.println("stub????");
            return (long) (new Random().nextGaussian() * 1000L);
        }
    }

    static class KlasImpl_Stub implements Klas {
        public long apply(Visitor v) {
            return v.visitKlas(this);
        }

        public long getRal() {
            System.out.println("stub????");
            return Thread.currentThread().getContextClassLoader().hashCode();
        }
    }

    static class ExprImpl implements Expr {
        public long apply(Visitor v) {
            return v.visitExpr(this);
        }

        public long getFoo() {
            return 1L;
        }
    }

    static class StmtImpl implements Stmt {
        public long apply(Visitor v) {
            return v.visitStmt(this);
        }

        public long getBar() {
            return 100000L;
        }
    }

    static class UnitImpl implements Unit {
        public long apply(Visitor v) {
            return v.visitUnit(this);
        }

        public long getBaz() {
            return 10000000000L;
        }
    }

    static class KlasImpl implements Klas {
        public long apply(Visitor v) {
            return v.visitKlas(this);
        }

        public long getRal() {
            return 0;
        }
    }
}

final class Abc {
    abstract static class Node {
        abstract long apply(Visitor v);
    }

    final static class Expr extends Node {
        final long apply(Visitor v) {
            return v.visitExpr(this);
        }

        final long getFoo() {
            return 1L;
        }
    }

    final static class Stmt extends Node {
        final long apply(Visitor v) {
            return v.visitStmt(this);
        }

        final long getBar() {
            return 100000L;
        }
    }

    final static class Unit extends Node {
        final long apply(Visitor v) {
            return v.visitUnit(this);
        }

        final long getBaz() {
            return 10000000000L;
        }
    }

    final static class Klas extends Node {
        final long apply(Visitor v) {
            return v.visitKlas(this);
        }

        final long getRal() {
            return 0;
        }
    }

    abstract static class Visitor {
        abstract long visitExpr(Expr n);
        abstract long visitStmt(Stmt n);
        abstract long visitUnit(Unit n);
        abstract long visitKlas(Klas n);
    }

    final static class PosVisitor extends Visitor {
        final long visitExpr(Expr n) { return n.getFoo(); }
        final long visitStmt(Stmt n) { return n.getBar(); }
        final long visitUnit(Unit n) { return n.getBaz(); }
        final long visitKlas(Klas n) { return n.getRal(); }
    }

    final static class NegVisitor extends Visitor {
        final long visitExpr(Expr n) { return -n.getFoo(); }
        final long visitStmt(Stmt n) { return -n.getBar(); }
        final long visitUnit(Unit n) { return -n.getBaz(); }
        final long visitKlas(Klas n) { return -n.getRal(); }
    }
}
