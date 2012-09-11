package com.midlocanics.frc.zooey;

import com.midlocanics.frc.gongaware.RobotComponent;
import com.midlocanics.frc.gongaware.Controller;
import com.midlocanics.frc.gongaware.part.Piston;

import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Ross Gongaware
 */
public class Shooter extends RobotComponent {
    public static final int BUTTON = 2;
    
    //Vitor controlling the shooting wheel    
    Victor vic;
    
    //Boolean indicating if the shooter is currently running    
    boolean isRunning;    
    
    Piston chute;
    
    long timeStart;
    
    
    public Shooter (Controller c, Victor vic, Piston p) {
        controller = c;
        
        //Make sure the motor is seen as false.        
        isRunning = false;
        
        this.vic = vic; 
        
        timeStart = 0;
        
        chute = p;        
    }
    
    public void reset() {
        chute.open();
    }
    
    //If it is running, keep running. If the button is pressed, run.    
    public boolean check() {
        return isRunning || controller.getButton(BUTTON);
    }
    
    public void run() {
        super.run();
        //If the button is pressed, toggle isRunning to turn the motor off.
        if (controller.getButton(BUTTON)) {
            if (isRunning) {
                isRunning = false;
                
                chute.close();
            }
            else {
                isRunning = true;
                
                timeStart = System.currentTimeMillis();
            }
        }
        
        //if isRunning is true, run the motor at full power, else turn it off        
        if (isRunning)
            vic.set(1.0);
        else
            vic.set(0.0);
        
        if (isRunning && !chute.isOpen() &&
                timeStart + (3.0*1000) < System.currentTimeMillis()) {
            chute.open();
            timeStart = 0;
        }
        
    }
    
}
