package frc.robot;

public class RobotMap {
  
// Joysticks and buttons
 public static int leftjoyPort = 1;
 public static int rightjoyPort = 0;

 public static int raisePort = 1;
 public static int lowerPort = 1;
 public static int manualraisePort = 5;
 public static int manuallowerPort = 3;
 public static int autoraisePort = 6;
 public static int autolowerPort = 4;


 

 public static int ballAdjustPort = 2; // right
 public static int resetPort = 12; // right
 public static int tapeAdjustPort = 1; // right
 public static int rangeLowerButton = 8; // left
 public static int rangeRaiseButton = 9; // left


 

 public static int cameraSwitchPort = 7;

  // CAN Bus Drive 
 public static int driveFrontRightPort = 7;
 public static int driveFrontLeftPort = 5;
 public static int driveBackRightPort = 2;
 public static int driveBackLeftPort = 1;

 // Sparks
 public static int driveFrontRightPortOld = 2;
 public static int driveFrontLeftPortOld = 0;
 public static int driveBackRightPortOld = 3;
 public static int driveBackLeftPortOld = 1;

 // Acquistion might be reversed
 public static int acquisitionPort = 3;
 public static int rollerPort = 4;

 // Lift Good
 public static int liftPort = 0;

  // Encoders All good
  public static int leftEncoderPort1 = 15;
  public static int leftEncoderPort2 = 16;
  public static int rightEncoderPort1 = 12;
  public static int rightEncoderPort2 = 13;
  public static int acquisition_encoderPort1 = 5;
  public static int acquisition_encoderPort2 = 6;
  public static int roller_encoderPort1 = 3;
  public static int roller_encoderPort2 = 4;
  public static int lifter_encoderPort1 = 8;
  public static int lifter_encoderPort2 = 9;

  // Old encoders
  public static int leftEncoderPort1Old = 4;
  public static int leftEncoderPort2Old = 3;
  public static int rightEncoderPort1Old = 5;
  public static int rightEncoderPort2Old = 6;

  // LED
  public static int relayPort = 1;
  // Magnetic Switches Lifter 2 and lifter 0 good
  public static int acquisition0Port = 2;
  public static int acquisition1Port = 1;
  public static int lifter1Port = 10;
  public static int lifter2Port = 0;
  public static int lifter0Port = 7;
/*
   // Old Robot
 
 public static int climberbuttonPort = 1;
 public static int togglegrabberbuttonPort = 2;
 public static int toggleextendbuttonPort = 7;
 public static int forward_axis = 1;
 public static int grabber_axis = 0;
  // Spark Motors
  public static int leftmotor1Port = 0;
  public static int leftmotor2Port = 1;
  public static int rightmotor1Port = 2;
  public static int rightmotor2Port = 3;
  public static int climbmotorPort = 4;
  public static int leftgrabberPort = 5;
  public static int rightgrabberPort = 6;
 */

 // Sensors 
  // public static int rangefinderPort = 7;
  // public static  SerialPort rangeFinderPort = new SerialPort(9600, SerialPort.Port.kOnboard);

}
