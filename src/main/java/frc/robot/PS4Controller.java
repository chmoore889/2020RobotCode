package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class PS4Controller extends Joystick {

    public PS4Controller(int port) {
        super(port);
    }

    //Buttons
    public boolean getXButton() {
        return getRawButton(2);
    }

    public boolean getOButton() {
        return getRawButton(3);
    }

    public boolean getSquareButton() {
        return getRawButton(1);
    }

    public boolean getTriangleButton() {
        return getRawButton(4);
    }

    public boolean getRightBumper() {
        return getRawButton(6);
    }

    public boolean getLeftBumper() {
        return getRawButton(5);
    }

    public boolean getLeftStickButton() {
        return getRawButton(11);
    }

    public boolean getRightStickButton() {
        return getRawButton(12);
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
        return getRawAxis(2);
    }

    public double getLeftTrigger() {
        return getRawAxis(3);
    }

    public double getRightTrigger() {
        return getRawAxis(4);
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