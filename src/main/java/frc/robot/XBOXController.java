package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class XBOXController extends Joystick {

    public XBOXController(int port) {
        super(port);
    }

    //Buttons
    public boolean getAButton() { //a button
        return getRawButton(1);
    }

    public boolean getBButton() { // b button
        return getRawButton(2);
    }

    public boolean getXButton() { //x button
        return getRawButton(3);
    }

    public boolean getYButton() { //y button
        return getRawButton(4);
    }

    public boolean getRightBumper() {
        return getRawButton(6);
    }

    public boolean getLeftBumper() {
        return getRawButton(5);
    }

    public boolean getLeftStickButton() {
        return getRawButton(9);
    }

    public boolean getRightStickButton() {
        return getRawButton(10);
    }

    //Analog sticks
    public double getLeftYAxis() {
        return getRawAxis(1);
    }

    public double getLeftXAxis() {
        return getRawAxis(0);
    }

    public double getRightYAxis() {
        return getRawAxis(5);
    }

    public double getRightXAxis() {
        return getRawAxis(4);
    }

    public double getLeftTrigger() {
        return getRawAxis(2);
    }

    public double getRightTrigger() {
        return getRawAxis(3);
    }

    //POV
    public boolean getDPadUp() {
        return getPOV() == 0;
    }

    public boolean getDPadRight() {
        return getPOV() == 90;
    }

    public boolean getDPadDown() {
        return getPOV() == 180;
    }

    public boolean getDPadLeft() {
        return getPOV() == 270;
    }
}