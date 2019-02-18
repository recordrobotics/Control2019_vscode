package frc.robot;

public class RobotMap {
  
// Joysticks and buttons
 public static int leftjoyPort = 0;
 public static int rightjoyPort = 1;

 public static int rollPort = 2;
 public static int raisePort = 1;
 public static int lowerPort = 1;
 public static int manualraisePort = 5;
 public static int manuallowerPort = 3;
 public static int autoraisePort = 6;
 public static int autolowerPort = 4;
 public static int switchliftstatePort = 11;


  // CAN Bus Drive
 public static int driveFrontRightPort = 7;
 public static int driveFrontLeftPort = 5;
 public static int driveBackRightPort = 2;
 public static int driveBackLeftPort = 1;
 public static int testMotorPort = 8;

 // Acquistion
 public static int acquisitionPort = 8;
 public static int rollerPort = 4;

 // Lift
 public static int liftPort = 0;

  // Encoders
  public static int leftEncoderPort1 = 4;
  public static int leftEncoderPort2 = 3;
  public static int rightEncoderPort1 = 5;
  public static int rightEncoderPort2 = 6;
  public static int acquisition_encoderPort1 = 1; 
  public static int acquisition_encoderPort2 = 2; 
  public static int lifter_encoderPort1 = 7;
  public static int lifter_encoderPort2 = 8;

  // Magnetic Switches
  public static int acquisition0Port = 0;
  public static int acquisition1Port = 12;
  public static int lifter1Port = 10;
  public static int lifter2Port = 11;
  public static int lifter0Port = 9;
   
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

 // Sensors 
  public static int rangefinderPort = 7;
  public static  SerialPort rangeFinderPort = new SerialPort(SerialPort.Port.kOnboard);

 */
}
