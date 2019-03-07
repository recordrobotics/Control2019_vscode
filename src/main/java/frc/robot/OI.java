package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	private static Joystick right = new Joystick(RobotMap.rightjoyPort);
  private static Joystick left = new Joystick(RobotMap.leftjoyPort);
  
	private static double forward;
  private static double rotation;
  private static int controltoggler = 0;
  private static boolean manualraisetoggler = false;
  private static boolean manuallowertoggler = false;
  private static boolean autoraisetoggler = false;
  private static boolean autolowertoggler = false;

  public static double getForward() {
    forward = 0.5*(left.getY() + right.getY());
    if(Math.abs(right.getY()) < 0.3 || Math.abs(left.getY()) < 0.3)
      forward = 0;
    return forward;
  }

	public static double getRotation() {
    rotation = 0.5*(right.getZ() + left.getZ());
    if(Math.abs(right.getZ()) < 0.3 || Math.abs(left.getZ()) < 0.3)
      rotation = 0;
		return rotation;
  }
  
  public Joystick getRightStick() {
    return right;
  }

  public Joystick getLeftStick() {
    return left;
  }
/*
  public static boolean getRightClimbButton() {
		return rightstick.getRawButton(RobotMap.climberbuttonPort);
	}

	public static boolean getLeftClimbButton() {
    return leftstick.getRawButton(RobotMap.climberbuttonPort);
  }
*/
  public static int getRollButton() {
    return ((left.getPOV() != -1 && (left.getPOV() >= 315 || left.getPOV() <= 45)) ? 1 : 0) - 
    ((left.getPOV() != -1 && (left.getPOV() >= 135 || left.getPOV() <= 225)) ? 1 : 0);
  }

  public static boolean getRaiseButton() {
    return right.getRawButton(RobotMap.raisePort);
  }

  public static boolean getLowerButton() {
    return left.getRawButton(RobotMap.lowerPort);
  }
/*
  public static boolean getAcquisitionRelease() {
    boolean release = (right.getRawButtonReleased(RobotMap.raisePort) && !left.getRawButton(RobotMap.lowerPort) ||
    left.getRawButtonReleased(RobotMap.lowerPort) && !right.getRawButton(RobotMap.raisePort));
    return release;
  }
*/
  public static boolean getManualRaiseButton() {
    boolean manualraise = left.getRawButton(RobotMap.manualraisePort);
    /*
    boolean manualraise = left.getPOV() != -1 && (left.getPOV() >= 315 || left.getPOV() <= 45);
    SmartDashboard.putNumber("left joy POV #", left.getPOVCount());
    SmartDashboard.putNumber("left joy POV", left.getPOV());
    if(manualraise){
      if(manualraisetoggler)
        manualraise = false;
      else 
        manualraise = true;
    }
    */
    return manualraise;
  }

  public static boolean getManualLowerButton() {
    boolean manuallower = left.getRawButton(RobotMap.manuallowerPort);
    /*
    boolean manuallower = left.getPOV() != -1 && (left.getPOV() >= 135 || left.getPOV() <= 225);
    SmartDashboard.putNumber("left joy POV #", left.getPOVCount());
    SmartDashboard.putNumber("left joy POV", left.getPOV());
    if(manuallower){
      if(manuallowertoggler)
        manuallower = false;
      else 
        manuallower = true;
    }
    */
    return manuallower;
  }
  
  public static boolean getManualRelease() {
    boolean release = (right.getRawButtonReleased(RobotMap.manualraisePort) && !right.getRawButton(RobotMap.manuallowerPort) ||
    right.getRawButtonReleased(RobotMap.manuallowerPort) && !right.getRawButton(RobotMap.manualraisePort));
    return release;
  }

  public static boolean getAutoRaiseButton() {
    boolean autoraise = right.getRawButtonReleased(RobotMap.autoraisePort);
    /*
    boolean autoraise = right.getPOV() != -1 && (right.getPOV() >= 315 || right.getPOV() <= 45);
    SmartDashboard.putNumber("right joy POV #", right.getPOVCount());
    SmartDashboard.putNumber("right joy POV", right.getPOV());
    
    if(autoraise){
      if(autoraisetoggler)
        autoraise = false;
      else 
        autoraise = true;
    }
    */
    return autoraise;
  }

  public static boolean getAutoLowerButton() {
    boolean autolower = right.getRawButtonReleased(RobotMap.autolowerPort);
    /*
    boolean autolower = right.getPOV() != -1 && (right.getPOV() >= 135 || right.getPOV() <= 225);
    SmartDashboard.putNumber("right joy POV #", right.getPOVCount());
    SmartDashboard.putNumber("right joy POV", right.getPOV());
    //System.out.println(autolower);
    
    if(autolower){
      if(autolowertoggler)
        autolower = false;
      else 
        autolower = true;
    }
    */
    return autolower;
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
