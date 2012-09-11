package com.midlocanics.frc.gongaware.part;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * 
 * Class representing the MAX Ultrasonic Sensor. Uses the value of 9.8mV/in to
 * determine the distance.
 *
 * @author Ross Gongaware
 */
public class UltrasonicSensor extends AnalogChannel {
    //9.8mV/in
    public static final double VOLTS_PER_INCH = .0098;
    
    /**
     * Gets the sensor at the given port.
     * @param port The Analog port of the sensor.
     */    
    public UltrasonicSensor (int port) {
        super(port);
    }
    
    /**
     * Gets the current distance as read from the sensor.
     * @return The range as read from the sensor.
     */    
    public double getDistance () {
        return getVoltage() / VOLTS_PER_INCH;
    }
    
}
