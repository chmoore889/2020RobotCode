package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;

public class Wheels { 

    private WPI_TalonSRX intake;
    private WPI_TalonSRX largeWheel;
    private WPI_TalonSRX shooter;
    private WPI_TalonSRX belt;
    private static AnalogInput ballSonar;
    public Wheels(){
        intake = new WPI_TalonSRX(4);
        largeWheel = new WPI_TalonSRX(5);
        shooter = new WPI_TalonSRX(6);
        belt = new WPI_TalonSRX(7);
        ballSonar = new AnalogInput(0);
    }
    //forward is from intake to shooters
    public void spinBigWheelFor(){
        largeWheel.set(ControlMode.PercentOutput, -0.3);
        belt.set(ControlMode.PercentOutput, 0.3);
    }

    public void spinBigWheelBac(){
        largeWheel.set(ControlMode.PercentOutput, 0.3);
        belt.set(ControlMode.PercentOutput, -0.3);
    }
    
    public void stopBigWheel(){
        largeWheel.set(ControlMode.PercentOutput, 0.0);
        belt.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void spinIntake(){
        intake.set(ControlMode.PercentOutput, -0.5);
    }
    
    public void stopIntake(){
        intake.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void spinShooter(){
        shooter.set(ControlMode.PercentOutput, 1.00);
    } 
    
    public void stopShooter(){
        shooter.set(ControlMode.PercentOutput, 0.0);
    }
    public static double printSonar(){
        return ballSonar.getValue() * .125;
    }
}
