package com.midlocanics.frc.gongaware.part;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * A representation of a piston. This is used as a convenience for operating two 
 * solenoids in tandem.
 * 
 * @author Ross Gongaware
 */
public class Piston {
    public static final int OPEN = 0;
    public static final int CLOSE = 1;
    
    private Solenoid[] valve;
    
    /**
     * Constructs a piston given the two {@link Solenoid}s that control it. 
     * Automatically sets the piston to closed.
     * 
     * @param open The Solenoid that when enables extends the piston.
     * @param close The Solenoid that closes it. 
     */    
    public Piston (Solenoid open, Solenoid close) {
        valve = new Solenoid[2];
            valve[OPEN] = open;
            valve[CLOSE] = close;
            
        valve[OPEN].set(false);
        valve[CLOSE].set(true);
    }
    
    /**
     * Returns if the piston is closed.
     * 
     * @return Boolean representation the state of the piston.
     */    
    public boolean isOpen() {
        return valve[OPEN].get();
    }
    
    /**
     * Opens piston if closed
     */    
    public void open() {
        if (!isOpen())
            toggle();
    }
    
    /**
     * Closes piston if closed.
     */    
    public void close() {
        if (isOpen())
            toggle();
    }
    
    /**
     * Toggles the piston into the other state.
     */    
    public void toggle() {
        if (valve[0].get()) {
            valve[1].set(true);
            valve[0].set(false);
        } else {
            valve[1].set(false);
            valve[0].set(true);
        }
    }
}
