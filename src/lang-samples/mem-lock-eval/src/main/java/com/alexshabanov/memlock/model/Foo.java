package com.alexshabanov.memlock.model;

public class Foo {
    private int a;

    private int b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Bar#{ a = " + a + ", b = " + b + " }";
    }
}
