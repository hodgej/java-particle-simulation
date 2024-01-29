package com.jackhodge;

import java.awt.*;

public class Particle {

    private double x;
    private double y;

    private double vX;
    private double vY;

    private final double MAX_MASS = 8;
    private double mass;

    private Color myColor;

    public Particle() {
        myColor = Color.BLUE;
        mass = Math.random() * MAX_MASS;
        this.x = 0;
        this.y = 0;
    }

    public Particle(double x, double y) {
        this();
        this.x = x;
        this.y = y;
    }

    public void updatePhysics(double dT){
        x += vX * dT;
        y += vY * dT;
        setColor( new Color(
                (int) clamp(0, x / 2, 255),
                (int) clamp(0, y / 2, 255),
                100
            ));
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public Color getColor() {
        return myColor;
    }


    public void addVelocity(double x, double y){
        vX += x;
        vY += y;
    }

    public void setVelocity(double x, double y){
        vX = x;
        vY = y;
    }

    public int getMass() {
        return (int) mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setColor(Color myColor) {
        this.myColor = myColor;
    }


    public static double clamp(double min, double current, double max){
        return Math.max(min, Math.min(max, current));
    }
}
