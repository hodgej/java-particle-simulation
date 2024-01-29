package com.jackhodge;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.SliderUI;
import java.awt.*;
import java.util.ArrayList;

/*
SimulationManager

Manages the application and hosts a ParticleSimulation instance which runs the simulation


 */

public class SimulationManager implements UIUpdateReceiver {

    ParticleSimulation mySimulation;

    public final static int GLOBAL_SIM_SIZE = 500;
    public final static int GLOBAL_MAX_PARTICLES = 1000;
    public final static int GLOBAL_MIN_PARTICLES = 0;
    public final static int GLOBAL_START_PARTICLES = 500;
    public final static int MOUSE_FORCE = 500000;
    public final static double DELTA_TIME = .0001;


    public void start(){
        System.out.println("Start sim");

        // Window //
        JFrame frame = new JFrame();
        frame.setTitle("Particle Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GLOBAL_SIM_SIZE, GLOBAL_SIM_SIZE);

        // UI //
        SimSlider slider = new SimSlider(this, this, GLOBAL_MIN_PARTICLES, GLOBAL_MAX_PARTICLES);
        JLabel sliderLabel = new JLabel("Particle Count: " );
        JPanel botttomLeft = new JPanel(new BorderLayout());

        botttomLeft.add(sliderLabel, BorderLayout.WEST);
        botttomLeft.add(slider, BorderLayout.EAST);

        frame.add(botttomLeft, BorderLayout.SOUTH);

        // Set up Particles //
        int particleCount = GLOBAL_START_PARTICLES;
        ArrayList<Particle> particles = new ArrayList<>();
        for(int pIndx = 0; pIndx < particleCount; pIndx++){
            Particle p = generateNewParticle();
            particles.add(p);
        }

        mySimulation = new ParticleSimulation(particles);
        frame.add(mySimulation);

        // Finish Setup
        frame.setVisible(true);

        System.out.println("Init success");

        run(frame, mySimulation);
    }

    // Run the ParticleSimulation
    public void run(JFrame frame, ParticleSimulation sim){
        while(true){
            sim.updateSimulation();
            try{
                Thread.sleep(10);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void sliderEvent(int value) {
        mySimulation.setParticles(value);
    }

    public static Particle generateNewParticle(){
         return new Particle(Math.random() * GLOBAL_SIM_SIZE, Math.random() * GLOBAL_SIM_SIZE);
    }

}
