package frc.robot.subsystems;  

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

public class Drivetrain {

    private static TalonSRX leftFront;
    private static TalonSRX leftBack;
    private static TalonSRX rightFront;
    private static TalonSRX rightBack;


    public Drivetrain() {
        leftFront = new TalonSRX(0);
        leftBack = new TalonSRX(1);
        rightFront = new TalonSRX(2);
        rightBack = new TalonSRX(3);

        leftBack.follow(leftFront);
        rightBack.follow(rightFront);

    }

    public void arcadeDrive(double straight, double left, double right) { 
        leftFront.set(ControlMode.PercentOutput, straight + left - right);
        rightFront.set(ControlMode.PercentOutput, -(straight - left + right)); 
    }

    public static void drive(double speed) {
        leftFront.set(ControlMode.PercentOutput, speed);
        rightFront.set(ControlMode.PercentOutput, speed);
    }

    public void tankDrive(double lspeed, double rspeed) {
        leftFront.set(ControlMode.PercentOutput, lspeed);
        rightFront.set(ControlMode.PercentOutput, rspeed);
    }
    
    public void setNeutralMode(NeutralMode mode) {
        leftFront.setNeutralMode(mode);
        leftBack.setNeutralMode(mode);
        rightFront.setNeutralMode(mode);
        rightBack.setNeutralMode(mode);
    }

    public static int getTicks() {
        //TODO return ticks
        return 0;
    }
}
