package com.midlocanics.frc.gongaware;

import com.midlocanics.frc.gongaware.Controller;

/**
 * Abstract class representing any component on a robot. This is used for a tick
 * based system where <code> check() </code> is called and when it returns true,
 * call the <code> run() </code>
 * @author Ross Gongaware
 */
public abstract class RobotComponent {
    //Th amount of milliseconds in a second. Used for timing purposes within
    //various components.
    private static final int MILLI = 1000;
    
    /**
     * Variable representing the controller for this component. Needed to
     * properly control each component.
     */
    protected Controller controller;
    
    private long lastRun;
    private long currentRun;
    
    /**
     * Checks to see if the component needs to run. If true, should be followed
     * by a <code> run() </code> call.
     * @return Whether component needs to be run
     */
    public abstract boolean check();
    
    /**
     * Runs the component. Should return quickly and not hold up the running of
     * the robot. Will be called successively.
     */
    public void run() {
        lastRun = currentRun;
        currentRun = System.currentTimeMillis();
    }
    
    /**
     * Resets the running of the component to it's initial state. Called at the 
     * beginning of tele-op once to ensure everything is in it's initial state.
     */
    public abstract void reset();
    
    /**
     * Gets the time since last run. Use this only if you also call <code> 
     * super.run(). </code>
     * @return The time since the last run in seconds.
     */    
    public double getTimeSinceLastRun() {
        return (double)(currentRun - lastRun)/MILLI;
    }
}
