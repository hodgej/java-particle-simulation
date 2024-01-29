package com.jackhodge;


// General Interface for any class subscribing to updates from UI elements
public interface UIUpdateReceiver {
    public void sliderEvent(int value);
}
