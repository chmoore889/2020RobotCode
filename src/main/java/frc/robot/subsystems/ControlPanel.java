package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.LinearFilter;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;

public class ControlPanel {
    
    private final double SPIN_MOTOR_SPEED = 0.5;
    private final double ARM_MOTOR_SPEED = 0.3;
    
    private final int FILTER_SAMPLES = 5;
    
    private WPI_TalonSRX arm;
    private WPI_TalonSRX spin;
    
    private ColorSensorV3 colorSensor;
    private final PossibleColor colorOrder[];
    private PossibleColor lastColor;
    
    private double timesToRotate; //Number of fulls rotations that still must be done to finish position control
    
    private LinearFilter filterB;
    private LinearFilter filterG;
    private LinearFilter filterR;
    private LinearFilter filterIR;
    
    private enum PossibleColor
    { 
        //TODO find correct color values
        BLUE(new RawColor(0, 0, 0, 0)),
        GREEN(new RawColor(0, 0, 0, 0)),
        RED(new RawColor(0, 0, 0, 0)),
        YELLOW(new RawColor(0, 0, 0, 0)); 
        
        private final RawColor color;
        
        PossibleColor(RawColor color) {
            this.color = color;
        }
        
        public RawColor getColor() {
            return color;
        }
    } 

    public ControlPanel() {
        arm = new WPI_TalonSRX(10);
        spin = new WPI_TalonSRX(11);
        
        colorSensor = new ColorSensorV3(Port.kOnboard);
        
        colorOrder = new PossibleColor[4];
        colorOrder[0] = PossibleColor.BLUE;
        colorOrder[1] = PossibleColor.GREEN;
        colorOrder[2] = PossibleColor.RED;
        colorOrder[3] = PossibleColor.YELLOW;
        
        lastColor = findCloseColor();
        
        timesToRotate = 0.0;
        
        filterB = LinearFilter.movingAverage(FILTER_SAMPLES);
        filterG = LinearFilter.movingAverage(FILTER_SAMPLES);
        filterR = LinearFilter.movingAverage(FILTER_SAMPLES);
        filterIR = LinearFilter.movingAverage(FILTER_SAMPLES);
    }
    
    //Resets all filters
    public void resetFilters() {
        filterB.reset();
        filterG.reset();
        filterR.reset();
        filterIR.reset();
    }
    
    //Turns wheel when the correct color is not reached & returns true if position is reached
    public boolean positionControl() {
        if(fieldSensorColor() != getIntendedColor()) {
            spin.set(SPIN_MOTOR_SPEED);
            return false;
        } else {
            spin.set(0.0);
            resetFilters();
            return true;
        }
    }
    
    //Queue up rotations of the control panel. Add in multiples of 0.125 as each segment is 0.125 of wheel total.
    public void addControlPanelRotation(double addTimesToRotate) {
        timesToRotate += addTimesToRotate;
    }
    
    //Turns control panel timesToRotate number of times
    public void rotationControl() {
        SmartDashboard.putNumber("Remaining Control Panel Rotations",timesToRotate);
        if(timesToRotate != 0) {
            spin.set(SPIN_MOTOR_SPEED);
            flipUpMotor();
            if(colorChange()) {
                timesToRotate -= 0.125 //Each color segment is 1/8 of wheel. This is subtracting a segment that still needs to be spun through.
            }
        }
        else {
            spin.set(0);
            flipDownMotor();
            resetFilters();
        }
    }
    
    public void flipUpMotor() {
        arm.set(ARM_MOTOR_SPEED);
    }
    
    public void flipDownMotor() {
        arm.set(-ARM_MOTOR_SPEED);
    }
    
    //Returns true if the current color is different from the last color
    private boolean colorChanged() {
        if(findCloseColor() == lastColor) {
            return false;
        }
        else {
            lastColor = findCloseColor();
            return true;
        }
    }
    
    //Return RawColor currently seen by field sensor based on color seen by the robot sensor
    private PossibleColor fieldSensorColor() {
        return colorOrder[(x + 2)%4];
    }

    //returns corresponding array index
    private int getArrayIndex() {
        PossibleColor color = findCloseColor();
        for(int x = 0; x < 4; x++) {
            if(color == colorOrder[x]) {
                return x;
            }
        }
    }
    
    //Returns RawColor currently seen by color sensor
    //Only a utility function to help other methods
    //Use findCloseColor() instead
    private RawColor getCurrentColor() {
        RawColor color = colorSensor.getRawColor();
        
        color.blue = filterB.calculate(color.blue);
        color.green = filterG.calculate(color.green);
        color.red = filterR.calculate(color.red);
        color.ir = filterIR.calculate(color.ir);
        
        return color;
    }
    
    private PossibleColor getIntendedColor() {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0) {
            switch (gameData.charAt(0)) {
                case 'B' :
                    return PossibleColor.BLUE;
                case 'G' :
                    return PossibleColor.GREEN;
                case 'R' :
                    return PossibleColor.RED;
                case 'Y' :
                    return PossibleColor.YELLOW;
                default :
                    System.out.println("Error: Corrupt data received");
                    break;
            }
        } 
        else {
            System.out.println("No data received");
        }
    }
    
    //Returns closest PossibleColor to current sensor color
    private PossibleColor findCloseColor() {
        RawColor currColor = getCurrentColor();
        blueDiff = colorDifference(currColor, PossibleColor.BLUE.getColor());
        greenDiff = colorDifference(currColor, PossibleColor.GREED.getColor());
        redDiff = colorDifference(currColor, PossibleColor.RED.getColor());
        yellowDiff = colorDifference(currColor, PossibleColor.YELLOW.getColor());
        
        int temp, size;
        int array[] = {blueDiff, greenDiff, redDiff, yellowDiff};
        size = array.length;
        
        for(int i = 0; i < size; i++) {
            for(int j = i+1; j < size; j++) {
                if(array[i] > array[j]) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        
        switch (array[0]) {
            case blueDiff:
                return PossibleColor.BLUE;
            case greenDiff:
                return PossibleColor.GREEN;
            case redDiff:
                return PossibleColor.RED;
            case yellowDiff:
                return PossibleColor.YELLOW;
        }
        return NULL;
    }
    
    //Returns the sum of each in raw color value of inputed color and PossibleColors
    private int colorDifference(RawColor color, RawColor possible) {
        return Math.abs(color.red - possible.red) + Math.abs(color.green - possible.green) +
            Math.abs(color.blue - possible.blue) + Math.abs(color.ir - possible.ir);
    }

}
