package com.alexshabanov.restcomp.exposure.shared.model;


public class NumHolder {
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static NumHolder fromNumber(int number) {
        final NumHolder result = new NumHolder();
        result.setNumber(number);
        return result;
    }
}
