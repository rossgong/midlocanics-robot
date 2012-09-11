package com.midlocanics.frc.zooey;

import com.midlocanics.frc.gongaware.RobotComponent;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Relay;

import com.midlocanics.frc.gongaware.Controller;

/**
 *
 * @author Ross Gongaware
 */
public class BridgeAccess extends RobotComponent {
    public static final int BUTTON = 4;
    
    //The two window motors conntrolling the bridge access system    
    Relay[] wins;
    
    //Boolean signifying if the wedge is up or down    
    boolean isDown;
    
    //boolean signifying whether the motors are currently running or not
    boolean isRunning;
    
    public BridgeAccess (Controller dual, Relay left, Relay right) {
        super.run();
        
        wins = new Relay [2];
            wins[0] = left;
            wins[1] = right;
            
        //intialize it as up and not running.       
        isDown = false;
        isRunning = false;
        
        //make sure the motors are tturned off
        wins[0].set(Relay.Value.kOff);
        wins[1].set(Relay.Value.kOff);
        
        this.controller = dual;
    }
    
    public void reset() {
        isDown = false;
        isRunning = false;
        
        wins[0].set(Relay.Value.kOff);
        wins[1].set(Relay.Value.kOff);
    }
    
    public boolean isDown() {
        return isDown;
    }
    
    //only run if it is already running or if the button is pressed    
    public boolean check () {
        return controller.getButton(BUTTON) || isRunning;
    }
    
    public void run() {
        super.run();
        
        if (!isRunning) isRunning = true;
        
        if (!controller.getRawButton(BUTTON)) {
            stop();
        } else if (!isDown) {
            putDown();
        } else {
            putUp();
        }
    }
    
    public void putUp () {
        isRunning = true;
        wins[0].set(Relay.Value.kForward);
        wins[1].set(Relay.Value.kReverse);
    }
    
    public void putDown() {
        isRunning = true;
        wins[0].set(Relay.Value.kReverse);
        wins[1].set(Relay.Value.kForward);
    }
    public void stop() {
        //Set is running to false
        isRunning = false;

        //actually turn the motors off
        wins[0].set(Relay.Value.kOff);
        wins[1].set(Relay.Value.kOff);

        //If it was up, set it to down, else set it to up.
        if (isDown) {
            isDown = false;
        } else {
            isDown = true;
        }
    }
}
