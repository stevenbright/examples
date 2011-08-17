package org.refsample.service.impl;

import org.refsample.service.Bird;


public class Eagle implements Bird {
    private final int index;

    public Eagle(int index) {
        this.index = index;
    }

    public void fly() {
        System.out.println("Eagle #" + index + " flying");
    }

    public void run() {
        System.out.println("Eagle #" + index + " running");
    }
}
