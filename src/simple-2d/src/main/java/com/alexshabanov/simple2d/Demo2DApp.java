package com.alexshabanov.simple2d;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Entry point.
 */
public final class Demo2DApp extends Frame {
    public static void main(String[] args) {
        System.out.println("Hello, world!");

        Demo2DApp f = new Demo2DApp();
        f.setTitle("PaintingAndStroking v1.0");
        f.setSize(300, 150);
        f.setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        double x = 15, y = 50, w = 70, h = 70;
        Ellipse2D e = new Ellipse2D.Double(x, y, w, h);
        GradientPaint gp = new GradientPaint(75, 75, Color.white,
                95, 95, Color.gray, true);
        // Fill with a gradient.
        g2.setPaint(gp);
        g2.fill(e);
        // Stroke with a solid color.
        e.setFrame(x + 100, y, w, h);
        g2.setPaint(Color.black);
        g2.setStroke(new BasicStroke(8));
        g2.draw(e);
        // Stroke with a gradient.
        e.setFrame(x + 200, y, w, h);
        g2.setPaint(gp);
        g2.draw(e);
    }
}
