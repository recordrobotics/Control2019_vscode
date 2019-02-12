/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  
  
  // Joysticks and buttons
 public static  int leftjoystickPort = 0;
 public static  int rightjoystickPort = 1;
 public static  int climberbuttonPort = 1;
 public static  int togglegrabberbuttonPort = 2;
 public static  int toggleextendbuttonPort = 7;
 public static  int forward_axis = 1;
 public static  int grabber_axis = 0;
  
  // Drive Sparks
 public static int driveFrontRightPort = 2;
 public static int driveFrontLeftPort = 0;
 public static int driveBackRightPort = 3;
 public static int driveBackLeftPort = 1;

 // Motors 
 public static int leftmotor1Port = 0;
 public static int leftmotor2Port = 1;
 public static  int rightmotor1Port = 2;
 public static  int rightmotor2Port = 3;
 public static  int climbmotorPort = 4;
 public static  int leftgrabberPort = 5;
 public static  int rightgrabberPort = 6;

 public static int liftmotorPort = 4;


 // Other actuators
 public static  int grabsolenoidmodulePort = 0;
 public static  int grabsolenoidforwardPort = 2;
 public static  int grabsolenoidbackwardPort = 3;
 public static  int extendsolenoidmodulePort = 0;
 public static  int extendsolenoidforwardPort = 0;
 public static  int extendsolenoidbackwardPort = 1;
 public static  int lightsPort = 0;

 // Sensors
 public static  int bottomswitchPort = 2;
 public static  int topswitchPort = 1;
 public static  int rangefinderPort = 0;

 public static  int innerswitchPort = 2;
 public static  int outerswitchPort = 1;
 //public static  SerialPort rangeFinderPort = new SerialPort(SerialPort.Port.kOnboard);

 // Encoders
 public static int leftEncoderPort1 = 4;
 public static int leftEncoderPort2 = 3;
 public static int rightEncoderPort1 = 5;
 public static int rightEncoderPort2 = 6;

}
