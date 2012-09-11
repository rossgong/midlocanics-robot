package com.midlocanics.frc.zooey;

import com.midlocanics.frc.gongaware.RobotComponent;
import edu.wpi.first.wpilibj.Victor;

import com.midlocanics.frc.gongaware.Controller;

/**
 * The component representing the polycord conveyer collection system.
 * @author Ross Gongaware
 */
public class BallCollection extends RobotComponent {
    
    /**
     * Button that activates this component.
     * 
     */
    public static final int BUTTON = 5;
    
    private Victor vic;
    
    private boolean isOn;
    
    /**
     * Creates a component with the ball collection 
     * {@link edu.wpi.first.wpilibj.Victor} and the 
     * {@link com.midlocanics.frc.gongaware.Controller} the user will be
     * controlling this component with.
     * @param dual
     * @param vic 
     */    
    public BallCollection (Controller dual, Victor vic) {
        this.vic = vic;
        
        isOn = false;
        
        this.controller = dual;
    }
    
    /**
     * Makes sure the system is off.
     */
    public void reset() {
        isOn = false;
    }
    
    /**
     * Checks to make sure it needs to run by first checking if it already
     * running or if it's button has been pressed.
     * @return Whether it needs to be run or not.
     */
    public boolean check() {
        return isOn || controller.getButton(BUTTON);
    }
    
    /**
     * Either turns it on or off based on whether it is already on or continues
     * running this component.
     */    
    public void run() {
        super.run();
        
        if (controller.getButton(BUTTON)) {
            if (isOn) isOn = false;
            else isOn = true;
        }
        
        if (isOn) {
            vic.set(-1.0);
        } else {
            vic.set(0.0);
        }
    }
}
