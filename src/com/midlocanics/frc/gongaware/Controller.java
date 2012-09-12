package com.midlocanics.frc.gongaware;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This is a class that provides convenience methods for any 
 * {@link edu.wpi.first.wpilibj.Joystick} with buttons used in a tick based
 * system. Use the <code> update() </code> method at the beginning of each tick
 * in order to get refresh it's state.
 * 
 * @author Ross Gongaware
 */

public class Controller extends Joystick {
    private int numButtons;
    
    private boolean[] currButtons;
    private boolean[] lastButtons;
    
    /**
     * Creates a controller on the given port and with a certain number of 
     * buttons.
     * 
     * @param port The port which this controller is connected. Make sure to
     *              check this when trying to debug that you are using the right
     *              port.
     * @param numButtons The number of buttons on the controller. If too little
     *                      the <code> update </code> method will not work with
     *                      all the buttons.
     */    
    public Controller (int port, int numButtons) {
        super(port);
        
        this.numButtons = numButtons;
        
        currButtons = new boolean[numButtons];
        lastButtons = new boolean[numButtons];
    }
    
    /**
     * Updates the controller to it's current state. Gets the current state of
     * the buttons and stores the previous state. Use this in conjunction with
     * <code> getButton() </code> in order to avoid your code from getting
     * excessive from a joystick.
     */
    public void update () {
        //set the previous state of buttons equal to the old current state.
        lastButtons = currButtons;
        
        //Set the current state equal to a completely new slate.
        currButtons = new boolean[numButtons];
        
        //Get the state if the buttons with the super classes getRawButton().
        for (int i = 0; i < numButtons; i ++) {
            currButtons[i] = super.getRawButton(i+1);
        }
    }
    
    /** 
     * Returns if the button was pressed as of last update. By keeping the
     * current state of the buttons and the previous I can determine if the
     * button was just pressed or being held. This is useful since the 
     * controller will checked faster then the driver will be able to press the
     * button. So instead of some action being done hundreds of times it will
     * instead only be activated once. It returns the current state of the 
     * button and the opposite of the previous state. This works as if the
     * current state has to be true and the previous state false to be
     * considered an actual press. If you need to know if an actual button is
     * being pressed, use <code> getRawButton() </code>. Requires the use of
     * <code> update() </code> to use properly. 
     * 
     * @param b The number of the button wished to be gotten
     * @return If the button was pressed between last tick and the current tick.
     * 
     */
    public boolean getButton(int b) {
        if (b >= numButtons || b < 1) return false;
        return currButtons[b-1] && (!lastButtons[b-1]);
    }
}
