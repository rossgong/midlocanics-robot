package com.midlocanics.frc.zooey;

import com.midlocanics.frc.gongaware.RobotComponent;
import edu.wpi.first.wpilibj.Relay;
import com.midlocanics.frc.gongaware.Controller;

/**
 * Child of {@link com.midlocanics.frc.gongaware.RobotComponent} representing
 * dumper mechanism. Controllers two window motors and moves it based on a single
 * button
 * @author Ross Gongaware
 */
public class Dumper extends RobotComponent {
    public static final int BUTTON = 3;
    
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    
    //The window motors controller the dumper    
    private Relay[] wins;
    
    //Boolean variable representing whether the dumper is up or not    
    private boolean isUp;
    
    //Boolean variable representing whether the motors are currently running
    private boolean isRunning;
    
    /**
     * Creates the dumper controller by the controller given and operating off
     * the two relays
     * 
     * @param dual The controller that contains the button operating this.
     * @param left The relay controlling left motor operating this
     * @param right The relay controlling right motor operating this
     */    
    public Dumper (Controller dual, Relay left, Relay right) {
        wins = new Relay [2];
            wins[0] = left;
            wins[1] = right;
            
           
        //Starat off assuming the dumper is down and isn;t running.
        isUp = false;
        isRunning = false;
        
        //Turn both of the motors off        
        wins[LEFT].set(Relay.Value.kOff);
        wins[RIGHT].set(Relay.Value.kOff);
        
        this.controller = dual;
    }
    
    public void reset() {
        isUp = false;
        isRunning = false;
        
        wins[LEFT].set(Relay.Value.kOff);
        wins[RIGHT].set(Relay.Value.kOff);
    }
    
    
    /**
     * Only passes the check if the button is down and it isn't already running.
     * 
     */
    public boolean check () {
        return controller.getButton(BUTTON) || isRunning;
    }
    
    /**
     * Runs this Dumper with special logic to determine what to do within this
     * "tick". The dumper was designed so that it could be controlled 
     * differently. Instead of pressing the button to make it go up, and then
     * pressing it again to go down, it was designed for it to be held to go in
     * one direction, and after being released and held down again, it would go
     * back down. This creates the need for the ability to detect when the
     * is held and let go. {@link com.midlocanics.frc.gongaware.Controller}'s
     * <code> update() </code> method only detects the first time it is pressed
     * down in order to stop events from triggering multiple times. This is one
     * of the few times where, while we can use <code> getButton() </code> to 
     * detect the initial button press to turn it on, we must use
     * <code> getRawButton() </code> to detect that the button is still being
     * held down.
     * 
     *  The logic used is simple. If the dumper isn't already running,
     * make it go down if it is up, or up if it is down. If it is already on,
     * it just checks to see if the button is being held down using <code>
     * getRawButton() </code>. If it is not being held, stop the dumper.
     * 
     */    
    public void run() {
        super.run();
        
        if (!isRunning) {
            if (isUp) {
                putDown();  
            } else {
                putUp();
            }
        }
        
        if (!controller.getRawButton(BUTTON)) {
            stop();
        }        
    }
    
    /**
     * Puts up by setting isRunning to true and then setting both relays to 
     * Forward. These values were gotten from trial and error and can be changed
     * by reversing the motors polarity.
     */    
    public void putUp () {
        isRunning = true;
        wins[0].set(Relay.Value.kForward);
        wins[1].set(Relay.Value.kForward);
    }
    
    /**
     * Puts up by setting isRunning to true and then setting both relays to 
     * Reverse. These values were gotten from trial and error and can be changed
     * by reversing the motors polarity.
     */        
    public void putDown() {
        isRunning = true;
        wins[0].set(Relay.Value.kReverse);
        wins[1].set(Relay.Value.kReverse);
    }
    
    /**
     * Stops the motor by turning off the motor and switching it's position from
     * up or down.
     */
    public void stop() {
        //Set is running to false
        isRunning = false;

        //actually turn the motors off
        wins[0].set(Relay.Value.kOff);
        wins[1].set(Relay.Value.kOff);

        //If it was up, set it to down, else set it to up.
        if (isUp) {
            isUp = false;
        } else {
            isUp = true;
        }
    }
}
