package com.midlocanics.frc.zooey;

import com.midlocanics.frc.gongaware.RobotComponent;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

import com.midlocanics.frc.gongaware.DualController;
import com.midlocanics.frc.gongaware.part.Piston;

/**
 *
 * @author Ross Gongaware
 */
public class DriveTrain extends RobotComponent {
    //Convienence constants to signify either the front or rear motor
    public static final int FRONT_MOTOR = 0;
    public static final int REAR_MOTOR = 1;
    
    //Same for the left and right sides for the pistons
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    
    //Buttons for Shift and reverseS
    public static final int REVERSE_BUTTON = 1;
    public static final int SHIFT_BUTTON = 8;
    
    public static final double CHANGE_PER_SECOND = 1.0;
    
    double[] lastSpeed;
    
    //Two Jaguar arrays representing the two left motors and the right motors
    Jaguar[] left;
    Jaguar[] right;
    
    //Robot Drive to control the robot
    RobotDrive drive;
    
    //boolean giving the reverse function
    boolean isHigh;    
    int reverse;
    
    //The contoller of this component    
    DualController dual;
    
    //The two shifters
    Piston[] shifters;
    
    boolean override;
    
    public DriveTrain (DualController controller,
                        Jaguar leftFront, Jaguar leftBack,
                        Jaguar rightFront, Jaguar rightBack,
                        Piston left, Piston right) {
        
        this.dual = controller;
        this.controller = controller;
        
        this.left = new Jaguar[2];        
            this.left[FRONT_MOTOR] = leftFront;
            this.left[REAR_MOTOR] = leftBack;
        
        this.right = new Jaguar[2];
            this.right[FRONT_MOTOR] = rightFront;
            this.right[REAR_MOTOR] = rightBack;
        
        this.drive = new RobotDrive(this.left[FRONT_MOTOR],
                                    this.left[REAR_MOTOR],
                                    this.right[FRONT_MOTOR],
                                    this.right[REAR_MOTOR]);
        
        //variable in which to multiply the joystick in order to get one of the
        //direction
        this.reverse = 1;
        
        //This represent the current gear.        
        this.isHigh = true;
        
        this.shifters = new Piston[2];
            this.shifters[LEFT] = left;
            this.shifters[RIGHT]= right;
            
        this.lastSpeed = new double[2];
            this.lastSpeed[0] = 0.0;
            this.lastSpeed[1] = 0.0;
            
        override = false;
            
    }
    
    public void reset() {
        isHigh = true;
        shifters[LEFT].close();
        shifters[RIGHT].close();
    }
    
    public void override() {
        override = true;
    }
    
    //Will always be in use    
    public boolean check() {
        return !override;
    }
    
    public void run() {
        super.run();
        
        //if you press the reverse button reverse the direction of the joysticks
        if (controller.getButton(REVERSE_BUTTON)) {
            reverse *= -1;
        }
        
        //shift and switch the variable when pressed and left go.        
        if (controller.getButton(SHIFT_BUTTON)) {
            shift();
            isHigh = true;
        } else if (!controller.getRawButton(SHIFT_BUTTON) && isHigh) {
            shift();
            isHigh = false;
        }
        
        //Get the joystick values and reverse if needed 
        double leftVal = dual.getLeftAxis(DualController.AxisType.kY)*reverse;
        double rightVal = dual.getRightAxis(DualController.AxisType.kY)*reverse;
        
        if (isHigh) {
            if (Math.abs(leftVal) > .75) {
                if (Math.abs(leftVal) - Math.abs(lastSpeed[LEFT]) > 
                        CHANGE_PER_SECOND*getTimeSinceLastRun()) {
                    if (leftVal>0) leftVal = lastSpeed[LEFT]+
                            (CHANGE_PER_SECOND*getTimeSinceLastRun());
                    else leftVal = lastSpeed[LEFT]-
                            (CHANGE_PER_SECOND*getTimeSinceLastRun());
                }
            }
            if (Math.abs(rightVal) > .75) {
                if (Math.abs(rightVal) - Math.abs(lastSpeed[RIGHT]) > 
                        CHANGE_PER_SECOND*getTimeSinceLastRun()) {
                    if (rightVal>0) rightVal = lastSpeed[RIGHT]+(CHANGE_PER_SECOND*getTimeSinceLastRun());
                    else rightVal = lastSpeed[RIGHT]-(CHANGE_PER_SECOND*getTimeSinceLastRun());
                }
            }
        }
        
        lastSpeed[LEFT] = leftVal;
        lastSpeed[RIGHT] = rightVal;
        
        
        //DRIVE MUTHAF*CKA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!        
        drive(leftVal*reverse, rightVal*reverse);
    }
    
    public void shift() {
        shifters[LEFT].toggle();
        shifters[RIGHT].toggle();
    }
    
    public void drive(double left, double right) {
        drive.tankDrive(left, right);
    }
}
