package com.midlocanics.frc.gongaware;

/**
 * Controller representing a Dualstick controller. Mainly provides convenience
 * methods for getting axis instead of using the twist and rotation axes for
 * the second stick.
 * @author Ross Gongaware
 */
public class DualController extends Controller {
    private static final AxisType LEFT_X = AxisType.kX;
    private static final AxisType LEFT_Y = AxisType.kY;
    
    private static final AxisType RIGHT_X = AxisType.kZ;
    private static final AxisType RIGHT_Y = AxisType.kThrottle;
    
    /**
     * Creates a dual stick controller from a certain port with the standard 12
     * buttons from a logitech dual action. Same as calling <p>
     * <code> DualController (port, 12) </code>
     * </p>
     * @param port The port that the dualstick resides.
     */
    public DualController (int port) {
        this(port, 12);
    }
    
    /**
     * Creates a dual stick with any number of buttons.
     * 
     * @param port The port which the controller resides
     * @param numButtons The number of buttons on the controller.
     */
    public DualController (int port, int numButtons) {
        super(port, numButtons);
    }
    
    /**
     * Gets either the x of y axis on the left stick.
     * 
     * @param axis Either <code> AxisType.kX </code> or
     *              <code> AxisType.kY </code>
     * @return  The value of said axis on the left stick.
     */
    
    public double getLeftAxis(AxisType axis) {
        if (axis == AxisType.kX) {
            return getAxis(LEFT_X);
        } else {
            return getAxis(LEFT_Y);
        }
    }
    
    /**
     * Gets either the x of y axis on the right stick.
     * 
     * @param axis Either <code> AxisType.kX </code> or
     *              <code> AxisType.kY </code>
     * @return  The value of said axis on the right stick.
     */    
    public double getRightAxis(AxisType axis) {
        if (axis == AxisType.kX) {
            return getAxis(RIGHT_X);
        } else {
            return getAxis(RIGHT_Y);
        }
    }
    
}
