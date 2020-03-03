package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;

public class Operator {

    private XBOXController OP;
    private Wheels wheels;
    private boolean shooting;
//    private ControlPanel controlPanel;

    // private boolean startedPositionControl;
    // private boolean finishedPositionControl;
    
    public Operator(int port) {
        OP = new XBOXController(port);
        wheels = new Wheels();
        shooting = false;
//        controlPanel = new ControlPanel();
//        resetControlPanel();
    }

    public void opControls() {
        controlPanelControl();        
        
        if(OP.getAButton()){
            wheels.spinIntake();
        } else if (!shooting) {
            wheels.stopIntake();
        }

        if (OP.getBButton()) {
            wheels.spinBigWheelFor();
        } else if (OP.getYButton()) {
            wheels.spinBigWheelBac();
        } else if (!shooting){
            wheels.stopBigWheel();
        }

        if(OP.getXButton()){
            wheels.spinShooter();
        } else if (!shooting){
            wheels.stopShooter();
        }

        if (OP.getRightTrigger() > 0.5){
            shooting = true;
        } else {
            shooting = false;
        }

        if(shooting) {
            wheels.spinIntake();
            wheels.spinBigWheelFor();
            wheels.spinShooter();
        }

        if (OP.getLeftTrigger() > 0.5){
            shooting = true;
        } else {
            shooting = false;
        }

        if(shooting) {
            wheels.spinIntake();
            wheels.spinBigWheelFor();
            wheels.spinShooter();
        }

        // OLD STUFF - GOTTA WORK THIS INTO THE CORRECT FORMAT FOR OPERATOR
        // if (OP.getXButton()) {
        //     wheels.spinBigWheelFor();
        // } else if (OP.getTriangleButton()) {
        //     wheels.spinBigWheelBac();
        // } else {
        //     wheels.stopBigWheel();
        // }

        // if (OP.getSquareButton()){
        //     wheels.spinIntake();
        // } else {
        //     wheels.stopIntake();
        // }

        // if (OP.getOButton()) {
        //     wheels.spinShooter();
        // } else {
        //     wheels.stopShooter();
        // }

        //if (OP.getOButton()) {
         //   if(wheels.printSonar()>0){ //this will be a correct number eventually
        //        wheels.spinShooter();
                //wheels.spinBigWheelFor();
        //    }
       //     else {
                //wheels.spinIntake();
       //     }
       // } 
        //else {
            //wheels.stopIntake();
         //   wheels.stopShooter();
            //wheels.stopBigWheel();
        //}
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
