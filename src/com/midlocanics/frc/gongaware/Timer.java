/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.midlocanics.frc.gongaware;

/**
 *
 * @author RossG
 */
public class Timer {
    
    private long countdownLength;
    private long startTime;
    
    public Timer (long milliTime) {
        countdownLength = milliTime;
        startTime = -1;
    }
    
    public Timer (double time) {
        this((long)(1000*time));
    }
    
    public void start() {
        startTime = System.currentTimeMillis();
    }
    
    public boolean timerDone() {
        if (startTime == -1) return false;
        return System.currentTimeMillis() - startTime > countdownLength;
    }
}
