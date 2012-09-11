package com.midlocanics.frc.zooey;

import com.midlocanics.frc.gongaware.RobotComponent;
import edu.wpi.first.wpilibj.Gyro;

import com.midlocanics.frc.gongaware.Controller;

/**
 *
 * @author Ross Gongaware
 */
public class BalanceSystem extends RobotComponent {
    public static final double SENSITIVITY = 1.0;
    
    public static final double TOLERANCE = 5.0;
    DriveTrain train;
    
    Gyro gyro;
    
    double lastAngle;
    
    boolean hasControl;
    boolean hasBeenInitialized;
    
    Controller[] controllers;
    
    public BalanceSystem (Controller a, Controller b, DriveTrain train, Gyro g) {
        this.controllers = new Controller[2];
            controllers[0] = a;
            controllers[1] = b;
        this.gyro = g;
        
        this.train = train;
        
        this.hasBeenInitialized = false;        
        
        lastAngle = 0.0;
        
        this.gyro.reset();
        
        hasControl = false;    
        
    }
    
    public void reset() {
        this.gyro.reset();
        lastAngle = 0.0;
        hasBeenInitialized = false;
    }
    
    public boolean check() {
        return (controllers[0].getRawButton(10) && 
                controllers[1].getRawButton(10)) || hasControl;
    }
    
    public void run () {
        super.run(); 
        
        if (!hasBeenInitialized) {
            lastAngle = Math.abs(gyro.getAngle());
            train.override();
            hasControl = true;
            hasBeenInitialized = true;
        }
        if (Math.abs(gyro.getAngle()) - Math.abs(lastAngle) > SENSITIVITY) {
            train.drive(0.0,0.0);
        } else {
            if (Math.abs(gyro.getAngle()) - TOLERANCE < 0.0) {
                train.drive(0.0,0.0);
            } else if (gyro.getAngle() < 0)
                train.drive(1.0, 1.0);
            else {
                train.drive(-1.0, -1.0);
            }
        }
    }
    
}
