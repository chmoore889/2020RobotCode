package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;

public class Operator {

    private Controller OP;
    private Wheels wheels;
//    private ControlPanel controlPanel;

    // private boolean startedPositionControl;
    // private boolean finishedPositionControl;
    
    public Operator(int port) {
        OP = new Controller(port);
        wheels = new Wheels();
//        controlPanel = new ControlPanel();
//        resetControlPanel();
    }

    public void opControls() {
        controlPanelControl();        
        
        if (OP.getXButton()) {
            wheels.spinBigWheelFor();
            System.out.println("attempting to spin big wheel forward");
        } else if (OP.getTriangleButton()) {
            wheels.spinBigWheelBac();
            System.out.println("attempting to spin big wheel backwards");
        } else {
            wheels.stopBigWheel();
            System.out.println("attempting to stop wheel");
        }

        if (OP.getOButton()) {
            if(wheels.printSonar()>0){ //this will be a correct number eventually
                wheels.stopIntake();
                wheels.spinBigWheelFor();
            }
            else {
                wheels.spinIntake();
            }
        } 
        else {
            wheels.stopIntake();
            wheels.stopBigWheel();
        }


        if (OP.getSquareButton()) {
            wheels.spinShooter();
        } else {
            wheels.stopShooter();
        }        
    }
    
    private void controlPanelControl(){
        // //Position control
        // if(OP.getRightBumper()) {
        //     startedPositionControl = true;
        //     flipUpMotor();
        // }
        
        // if(startedPositionControl && !finishedPositionControl){
        //     finishedPositionControl = positionControl();
        // }
        
        // if(finishedPositionControl) {
        //     flipDownMotor();
        //     resetControlPanel();
        // }
        
        // //Rotation control
        // if(OP.getLeftBumper()) {
        //     addControlPanelRotation(1);//Each button press queues another rotation for the motor to spin through
        // }
        // rotationControl();
    }
            
    // private void resetControlPanel() {
    //     startedPositionControl = false;
    //     finishedPositionControl = false;
    // }
}
