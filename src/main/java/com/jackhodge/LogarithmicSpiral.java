package com.jackhodge;

import java.awt.*;
import javax.swing.*;

public class LogarithmicSpiral extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int numPoints = 1000;
        double goldenRatio = (1 + Math.sqrt(5)) / 2;
        double a = 10;  // Spiral tightness factor
        double b = 0.2; // Spiral growth factor

        for (int i = 0; i < numPoints; i++) {
            double t = i * Math.PI * 2;
            double x = centerX + (a * Math.pow(goldenRatio, b * t) * Math.cos(t));
            double y = centerY + (a * Math.pow(goldenRatio, b * t) * Math.sin(t));
            g.fillOval((int)x, (int)y, 4, 4);
        }
    }


}
