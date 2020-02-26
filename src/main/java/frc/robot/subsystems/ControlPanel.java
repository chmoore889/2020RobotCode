package frc.robot.subsystems;

import java.awt.Color;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.I2C.Port;

public class ControlPanel {
    
    private static WPI_TalonSRX actuator;
    private static WPI_TalonSRX spinMotor;

    public ControlPanel(){
        actuator = new WPI_TalonSRX(0);
        spinMotor = new WPI_TalonSRX(0);

    }

    public void stopAtColor(){
        
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
            switch (gameData.charAt(0))
            {
                case 'B' :
                //Blue case code
                break;
                case 'G' :
                //Green case code
                break;
                case 'R' :
                //Red case code
                break;
                case 'Y' :
                //Yellow case code
                break;
                default :
                //This is corrupt data
                break;
            }
        }    
        else {
        //Code for no data received yet
        }

        final ColorSensorV3 sensor = new ColorSensorV3(Port.kMXP);
        RawColor green = new RawColor(0, 0, 0, 0);
        RawColor blue = new RawColor(0, 0, 0, 0);
        RawColor yellow = new RawColor(0, 0, 0, 0);
        RawColor red = new RawColor(0, 0, 0, 0);

        RawColor color = new RawColor(sensor.getRed(), sensor.getGreen(), sensor.getBlue(), sensor.getIR());
        
        if(green.equals(color))
        {
            spinMotor.set(ControlMode.PercentOutput, 0.0);
        }

        

    }

}