/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.midlocanics.frc.gongaware;

import edu.wpi.first.wpilibj.SimpleRobot;

/**
 * A basic framework for a robot based on 
 * {@link com.midlocanics.frc.gongaware.RobotComponent}s. Will run each
 * component for all of tele-op. Autonomous must be defined, but the component
 * system makes this much easier with proper component definitions.
 * @author RossG
 */
public abstract class AbstactComponentRobot extends SimpleRobot {
    
    /**
     * An array of controllers being used to control the bot.
     */    
    protected Controller[] controllers;
    
    /**
     * An array of the components within the bot.
     */
    protected RobotComponent[] components;
    
    /**
     * Initializes the basic controllers and components. Should call
     * <code> intializeComponents() </code> after finishing.
     */    
    abstract public void robotInit();
    
    /**
     * Initializes all the individual components. Assume the arrays needed have
     * already been defined, but all parts should be initialized here too.
     */    
    abstract protected void initializeComponents();
    
    abstract public void autonomous();
    
    /**
     * Resets anything that needs to be reset before being run.
     * 
     */    
    abstract public void reset();
    
    /**
     * Updates the controllers and then checks and runs each component. Runs for
     * as long as the operator has control.
     * 
     */ 
    public void operatorControl() {
        for (int i = 0; i < controllers.length; i++) {
            controllers[i].update();
        }
        
        for (int i = 0; i < components.length; i++) {
            RobotComponent c = components[i];

            //check to see if the component needs to run, and run if so
            if (c.check()) {
                c.run();
            }
        }
    }
    
    /**
     * Provides a convenient pause method for autonomous. SHOULD ONLY BE USED 
     * FOR AUTONOMOUS! Use in any component could cause other component to 
     * become unresponsive.
     * @param milliTime The amount of time to pause.
     */
    public static void pause(long milliTime) {
        Timer t = new Timer(milliTime);
        
        t.start();
        while (!t.timerDone()) {}
    }
    
    /**
     * Same as the other pause but can use double for time. equivalent to 
     * calling <code> pause((long)(time*1000)) </code>
     * @param time amount of time to pause
     */    
    public static void pause(double time) {
        pause((long)(1000*time));
    }
}
