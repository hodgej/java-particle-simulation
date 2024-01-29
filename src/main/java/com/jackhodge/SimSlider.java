package com.jackhodge;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SimSlider extends JSlider{
    private JLabel label;

    SimulationManager parent;


    public SimSlider(SimulationManager parent, UIUpdateReceiver updateReceiver, int min, int max) {
        super(JSlider.HORIZONTAL, min, max, (int) max/2);

        this.setValue(SimulationManager.GLOBAL_START_PARTICLES);

        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateReceiver.sliderEvent(getValue());
            }
        });
    }
}
