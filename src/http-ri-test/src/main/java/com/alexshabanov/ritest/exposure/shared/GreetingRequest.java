package com.alexshabanov.ritest.exposure.shared;

/**
 * Wraps request parameters for greeting object.
 */
public final class GreetingRequest {
    private String origin;
    private int warmLevel;
    private double sincerity;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getWarmLevel() {
        return warmLevel;
    }

    public void setWarmLevel(int warmLevel) {
        this.warmLevel = warmLevel;
    }

    public double getSincerity() {
        return sincerity;
    }

    public void setSincerity(double sincerity) {
        this.sincerity = sincerity;
    }

    @Override
    public String toString() {
        return "GreetingRequest{" +
                "origin='" + getOrigin() + '\'' +
                ", warmLevel=" + getWarmLevel() +
                ", sincerity=" + getSincerity() +
                '}';
    }
}
