/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);
	private static Joystick rightstick = new Joystick(RobotMap.rightjoystickPort);
	private static Joystick leftstick = new Joystick(RobotMap.leftjoystickPort);
	private static double forward;
  private static double rotation;
  
	
	public static double getRotation() {
    rotation = (rightstick.getZ() + leftstick.getZ())/2;
    if(Math.abs(rightstick.getZ()) < 0.3 || Math.abs(leftstick.getZ()) < 0.3)
      rotation = 0;
		return rotation;
	}
	
	public static double getForward() {
    forward = (leftstick.getY() + rightstick.getY())/2;
    if(Math.abs(rightstick.getY()) < 0.3 || Math.abs(leftstick.getY()) < 0.3)
      forward = 0;
    return forward;
  }
  
  public Joystick getRightStick() {
    return rightstick;
  }

  public Joystick getLeftStick() {
    return leftstick;
  }

  public static boolean getRightClimbButton() {
		return rightstick.getRawButton(RobotMap.climberbuttonPort);
	}

	public static boolean getLeftClimbButton() {
		return leftstick.getRawButton(RobotMap.climberbuttonPort);
	}
  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
