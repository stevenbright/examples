package com.alexshabanov.memlock.model;

public class Bar {
    private int a;

    private Integer b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }


    @Override
    public String toString() {
        return "Bar#{ a = " + a + ", b = " + b + " }";
    }
}
