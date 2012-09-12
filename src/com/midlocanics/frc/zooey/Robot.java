package com.midlocanics.frc.zooey;

import com.midlocanics.frc.gongaware.RobotComponent;
import com.midlocanics.frc.gongaware.part.UltrasonicSensor;
import com.midlocanics.frc.gongaware.part.Piston;
import com.midlocanics.frc.gongaware.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Gyro;



/**
 * REBOUND RRRRRUUUUUUUMMMMMMBBBBBLLLLLEEEEEEE!!!!!!
 *
 * @author Ross Gongaware
 */
public class Robot extends AbstactComponentRobot {
    //Index of each component
    private final int DRIVE_TRAIN = 0;
    private final int BALL_COLLECTION = 1;
    private final int SHOOTER = 2;
    private final int BALANCE = 3;
    private final int DUMPER = 4;
    private final int BRIDGE_ACCESS = 5;
    
    //Self evident    
    public static final int NUM_COMPONENTS = 6;
    
    //Index of the primary and utility controller. The primary will be used for
    //driving while the utility will be used for everything.
    private final int PRIMARY = 0;
    private final int UTILITY = 1;
    
    //Boolean for whether autonomous should be intelligent or dead wreckoning.    
    private final boolean DEAD = true;
    
    Compressor comp;
   
    /*
     * If you have any questions with this code, you can try and contact me
     * at rjgongaw@ncsu.edu or ross.gong@gmail.com. Will respond faster and 
     * would prefer the former.
     * -Ross Gongaware
     */
    public void robotInit() {
        controllers = new DualController[2];
            controllers[PRIMARY] = new DualController(1, 12);
            controllers[UTILITY] = new DualController(2, 12);

        comp = new Compressor(1, 5);

        components = new RobotComponent[NUM_COMPONENTS];
        initializeComponents();
    }

    /**
     * Initializes all the components relevant this robot and stores them in an
     * array.
     */
    protected void initializeComponents() {
        Piston[] shifters = new Piston[2];
            shifters[0] = new Piston(new Solenoid(1), new Solenoid(2));
            shifters[1] = new Piston(new Solenoid(3), new Solenoid(4));

        Jaguar[] left = new Jaguar[2];
            left[0] = new Jaguar(1);
            left[1] = new Jaguar(2);
        Jaguar[] right = new Jaguar[2];
            right[0] = new Jaguar(3);
            right[1] = new Jaguar(4);

        components[DRIVE_TRAIN] = new DriveTrain(
                (DualController)controllers[PRIMARY],
                left[0], left[1],
                right[0], right[1],
                shifters[0], shifters[1]);

        Victor vic = new Victor(5);

        components[BALL_COLLECTION] = new BallCollection(
                                                     controllers[PRIMARY], vic);

        vic = new Victor(6);
        Piston p = new Piston(new Solenoid(5), new Solenoid(6));

        components[SHOOTER] = new Shooter(controllers[PRIMARY], vic, p);

        Gyro gyro = new Gyro(1);

        components[BALANCE] = new BalanceSystem(controllers[PRIMARY], 
                controllers[PRIMARY], (DriveTrain) components[DRIVE_TRAIN],
                gyro);
        
        Relay[] dump = new Relay[2];
            dump[0] = new Relay(3);
            dump[1] = new Relay(4);
        components[DUMPER] = new Dumper(controllers[PRIMARY], dump[0], dump[1]);
        
                   
        Relay[] wedge = new Relay[2];
            wedge[0] = new Relay(1);
            wedge[1] = new Relay(2);
            
        components[BRIDGE_ACCESS] = new BridgeAccess(controllers[PRIMARY], 
                wedge[0], wedge[1]);
    }

    /**
     * Called during autonomous.
     */
    public void autonomous() {
        
        System.out.println("start:auto");
        System.out.println("start:init");
        
        double time;
        Timer t;
        DriveTrain train = (DriveTrain) components[DRIVE_TRAIN];
        Dumper dump = (Dumper) components[DUMPER];
        UltrasonicSensor s = new UltrasonicSensor(2);
        
        train.reset();
        System.out.println("end:init");
        
        System.out.println("start:forward");        
        if (DEAD) {            
            time = 3.5;
            t = new Timer(time);
            
            while (!t.timerDone()) {
                train.drive(.79, .89);
            }
        } else {
            while (s.getDistance() < 42) {
                train.drive(.8,.8);
            }
        }    
        train.drive(0.0, 0.0);    
        System.out.println("end:forward");        
        
        
        System.out.println("start:dump:up");
        time = 2.0;
        t = new Timer(time);
        dump.putUp();
        pause(time);
        dump.stop();
        System.out.println("end:dump:up");
        
        System.out.println("start:dump:pause");                
        time = 1.0;
        pause(time);
        System.out.println("end:dump:pause"); 
        
        System.out.println("start:dump:down"); 
        dump.putDown();        
        time = 2.0;  
        pause(time);        
        dump.stop();
        System.out.println("end:dump:down"); 
        System.out.println("end:auto"); 
    }

    public void reset() {
        //Reset each component
        for (int i = 0; i < components.length; i++) {
            components[i].reset();
        }

        //start the compressor
        comp.start();
    }
}
