package com.jackhodge;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
ParticleSimulation

Updates each particle every run() call by SimulationManager.

 */

public class ParticleSimulation extends JPanel {

    ArrayList<Particle> particles;

    private final int particleViewScalar = 4;
    private final double repulsionConstant = 1e+3;

    public ParticleSimulation(ArrayList<Particle> particles){
        this.particles = particles;
    }

    // Update Each Particle
    public void updateSimulation(){
        ArrayList<Particle> particlesCopy = new ArrayList<>(particles);

        // dummy particle for cursor
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();

        Particle cursorDummy = new Particle(mousePoint.x, mousePoint.y);

        for(Particle p : particlesCopy){
            // update velocity

            // cursor
            double[] mouseForces = calculateForceComponents(SimulationManager.MOUSE_FORCE, p, cursorDummy);
            System.out.println("Mouse force: " + mouseForces[0] + ", " + mouseForces[1]);
            p.addVelocity(mouseForces[0], mouseForces[1]);
            // all other particles

            for(Particle otherParticle : particlesCopy){
                if(otherParticle == p) continue;


                double[] forces = calculateForceComponents(repulsionConstant, p, otherParticle);

               p.addVelocity(forces[0]/p.getMass(), forces[1]/p.getMass());
            }

            // wall repulsion
            double distToRightWall = SimulationManager.GLOBAL_SIM_SIZE-p.getX();
            // where x=0=-250; x=250=0; x=500=250;
            double equalizedWallDistance = distToRightWall - (double) SimulationManager.GLOBAL_SIM_SIZE / 2;
            // ceiling repulsion
            double distToBottomCeiling = SimulationManager.GLOBAL_SIM_SIZE-p.getY();
            // where x=0=-250; x=250=0; x=500=250;
            double equalizedCeilingDistance = distToBottomCeiling - (double) SimulationManager.GLOBAL_SIM_SIZE / 2;

            double wallForceX = wallRepulsionFunction((int) equalizedWallDistance);
            double wallForceY = wallRepulsionFunction((int) equalizedCeilingDistance);

            p.addVelocity(wallForceX, wallForceY);


            // update position
            p.updatePhysics(SimulationManager.DELTA_TIME);
        }
        repaint();
    }


    public double wallRepulsionFunction(double x){
        return (Math.pow(x, 3) / 15000) - (x / 5);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for(Particle p : particles){
            g2d.setColor(p.getColor());
            g2d.fillOval(p.getX(), p.getY(), p.getMass() * particleViewScalar, p.getMass() * particleViewScalar);
        }
    }

    public void setParticles(int num){
        // Remove particles until = num
        while(particles.size() > num){
            particles.remove(0);
        }
        // Add particles until = num
        while(particles.size() < num) {
            particles.add(SimulationManager.generateNewParticle());
        }

    }

    public double[] calculateForceComponents(double repulsive_force, Particle p, Particle otherParticle){
        // Calculate the repulsive force between two particles
        double diffX = p.getX() - otherParticle.getX();
        double diffY = p.getY() - otherParticle.getY();

        double distSquared = Math.pow(diffX, 2) + Math.pow(diffY, 2);

        double distance = Math.sqrt(distSquared);

        double force = (repulsive_force * otherParticle.getMass() * p.getMass()) / distSquared;

        // get components of force
        double force_x = force * diffX / distance;
        double force_y = force * diffY / distance;

        return new double[]{force_x, force_y};

    }
}
