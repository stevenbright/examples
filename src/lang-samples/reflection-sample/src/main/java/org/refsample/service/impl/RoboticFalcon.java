package org.refsample.service.impl;


import org.refsample.service.Bird;
import org.refsample.service.Robot;

public class RoboticFalcon implements Bird, Robot {
    private final int index;

    public RoboticFalcon(int index) {
        this.index = index;
    }

    public void fly() {
        System.out.println("Robotic Falcon #" + index + " flying");
    }

    public void run() {
        System.out.println("Robotic Falcon #" + index + " running");
    }

    public void moveTo(int x, int y) {
        System.out.println("Robotic Falcon #" + index + " is asked to move to x=" + x + ", y=" + y);
    }
}
