package com.jackhodge;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.SliderUI;
import java.awt.*;
import java.util.ArrayList;

public class SimulationManager implements UIUpdateReceiver {

    ParticleSimulation mySimulation;

    public final static int GLOBAL_SIM_SIZE = 500;
    public final static int GLOBAL_MAX_PARTICLES = 1000;
    public final static int GLOBAL_MIN_PARTICLES = 0;
    public final static int GLOBAL_START_PARTICLES = 500;
    public final static int MOUSE_FORCE = 500000;
    public void start(){
        System.out.println("Start sim");

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GLOBAL_SIM_SIZE, GLOBAL_SIM_SIZE);

        // UI //
        SimSlider slider = new SimSlider(this, this, "Particle Count: ", GLOBAL_MIN_PARTICLES, GLOBAL_MAX_PARTICLES);

        JPanel botttomLeft = new JPanel(new BorderLayout());

        botttomLeft.add(slider, BorderLayout.WEST);

        frame.add(botttomLeft, BorderLayout.SOUTH);


        // Setup Particles //
        int particleCount = GLOBAL_START_PARTICLES;
        ArrayList<Particle> particles = new ArrayList<>();
        for(int pIndx = 0; pIndx < particleCount; pIndx++){
            Particle p = generateNewParticle();
            particles.add(p);
        }

        mySimulation = new ParticleSimulation(particles);
        frame.add(mySimulation);

        frame.setVisible(true);

        System.out.println("Init success");
        run(frame, mySimulation);
    }


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
        System.out.println("Set value: " + value);
        mySimulation.setParticles(value);
    }


    public static Particle generateNewParticle(){
         return new Particle(Math.random() * GLOBAL_SIM_SIZE, Math.random() * GLOBAL_SIM_SIZE);
    }
}
