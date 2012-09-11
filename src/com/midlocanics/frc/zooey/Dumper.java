package com.midlocanics.frc.zooey;

import com.midlocanics.frc.gongaware.RobotComponent;
import edu.wpi.first.wpilibj.Relay;
import com.midlocanics.frc.gongaware.Controller;

/**
 *
 * @author Ross Gongaware
 */
public class Dumper extends RobotComponent {
    public static final int BUTTON = 3;
    
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    
    //Thw indow motors controller the dumper    
    Relay[] wins;
    
    //Boolean variable representing whether th dumper is up or not    
    boolean isUp;
    
    //Boolean variable representing whether the motors are currently running
    boolean isRunning;
    
    public Dumper (Controller dual, Relay left, Relay right) {
        wins = new Relay [2];
            wins[0] = left;
            wins[1] = right;
            
           
        //Tell me that the dumper is down and isnt running
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
    
    
    //Only run when it either running or if the button is pressed    
    public boolean check () {
        return controller.getButton(BUTTON) || isRunning;
    }
    
    public void run() {
        super.run();
        
        if (!isRunning) {
            if (isUp)
                putDown();  
            else
                putUp();
        }
        
        if (!controller.getRawButton(BUTTON)) {
            stop();
        }        
    }
    
    public void putUp () {
        isRunning = true;
        wins[0].set(Relay.Value.kForward);
        wins[1].set(Relay.Value.kForward);
    }
    
    public void putDown() {
        isRunning = true;
        wins[0].set(Relay.Value.kReverse);
        wins[1].set(Relay.Value.kReverse);
    }
    
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
