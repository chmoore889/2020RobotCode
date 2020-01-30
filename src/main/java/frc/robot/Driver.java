package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Driver {
    Controller joy;
    Drivetrain drive;

    SendableChooser<Byte> driveType;
    private final Byte arcade = 0;
    private final Byte tank = 1;
    
    SendableChooser<Byte> neutralModeType;//Sets whether motors brake or coast
    private final Byte brake = 0;
    private final Byte coast = 1;
    
    DriveThread driveThread;

    public Driver(int port) {
        joy = new Controller(port);
        drive = new Drivetrain();

        //Init driveType
	driveType = new SendableChooser<>();
        driveType.setDefaultOption("Arcade", arcade);
        driveType.addOption("Tank", tank);
        SmartDashboard.putData("Drive Type", driveType);
        
        //Init neutralModelType
        neutralModeType = new SendableChooser<>();
        neutralModeType.setDefaultOption("Brake", brake);
        neutralModeType.addOption("Coast", coast);
        SmartDashboard.putData("Drivetrain Coast Type", neutralModeType);
    }
    
    public void initDriveThread() {
        //Thread initialization
        driveThread = new DriveThread(this);
	    new Thread(driveThread).start();
    }
	
    private class DriveThread implements Runnable {
	Drivetrain drive;
	Controller joy;
	SendableChooser<Byte> driveType;
	SendableChooser<Byte> neutralModeType;

	public DriveThread(Driver driver) {
		this.drive = driver.drive;
        this.joy = driver.joy;
		this.driveType = driver.driveType;
		this.neutralModeType = driver.neutralModeType;
	}

    public void run() {
	    while (true) {
	        try {
                Thread.sleep(20);//Sleep for 20ms to reduse used network bandwidth
	        } 
	        catch (Exception e) {
	        }
            runDriveControls();
	    }
	}
        
        private void runDriveControls() {
            //driveType Selection
            if(driveType.getSelected().equals(arcade)) {
                drive.arcadeDrive(joy.getRightYAxis(), joy.getLeftTrigger(), joy.getRightTrigger());
                //System.out.println("Y Axis: " + joy.getRightYAxis() + "     Left: " + joy.getLeftTrigger() + "     Right: " + joy.getRightYAxis());
            } 
            else if(driveType.getSelected().equals(tank)) {
                drive.tankDrive(joy.getLeftYAxis(), joy.getRightYAxis());
            } 
            else {
                System.out.println("Error: No drive type chosen");
            }

            //neutralModeType Selection
            if(neutralModeType.getSelected().equals(brake)) {
                drive.setNeutralMode(NeutralMode.Brake);
            }
	    else if(neutralModeType.getSelected().equals(coast)) {
                drive.setNeutralMode(NeutralMode.Coast);
            }
            else {
                System.out.println("Error: No coast type chosen");
            }
        }
    }
}
