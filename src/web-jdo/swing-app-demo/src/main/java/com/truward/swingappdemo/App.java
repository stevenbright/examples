package com.truward.swingappdemo;

import javax.swing.*;

/**
 * Entry point
 */
public class App {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("HelloWorldSwing");
        final JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
