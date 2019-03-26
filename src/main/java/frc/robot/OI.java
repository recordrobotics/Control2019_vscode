package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.Reset;
import frc.robot.commands.CenterTape;
import frc.robot.commands.NetworkCommand;
import frc.robot.commands.PIDSpin;


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
  private static Joystick buttons = new Joystick(RobotMap.buttonPanelPort);
  
	private static double forward;
  private static double rotation;
  private static int controltoggler = 0;
  private static boolean manualraisetoggler = false;
  private static boolean manuallowertoggler = false;
  private static boolean autoraisetoggler = false;
  private static boolean autolowertoggler = false;

  private static final double l_joystick_sens = 0.35;
  private static final double r_joystick_sens = 1.0;
  //for Alex set at l = 0.35 and r = 1
  

  public OI() {
    JoystickButton but = new JoystickButton(right, RobotMap.resetPort);
    //but.whenPressed(new Reset(4000));

    /*double x = 1.0;
		double y = 3.0;
		double a = 0.0;
		SmartDashboard.delete("auto_move");
		Network n = Robot.getNetwork(0);
		if(n != null) {
			System.out.println("Network called");
			//but.whenPressed(new NetworkCommand(n, x, y, a, 0.2, 10000, true));
    }
    but.whenPressed(new PIDSpin(180.0, true, 4000, 1.0));
    (new JoystickButton(right, 11)).whenPressed(new PIDSpin(0.0, true, 4000, 1.0));*/
  }

  // public static int getRightPOV() {
  //   return right.getPOV();
  // }
  // public static int getButtonPOV(){
  //   System.println(buttons.getPOV());
  //   return buttons.getPOV();
  // }

  public static double getOldLifter(){
    int joyStickStateRight = right.getPOV(); // returns state of joystick
    System.out.println("getOldLifter is called with" + joyStickStateRight);
    if(joyStickStateRight == 0){
      return 1.0;
    }
    if(joyStickStateRight == 180){
      return -1.0;
    } 
    return 0;
  }

  /*public static boolean getRangeButtonPressed() {
    return left.getRawButtonReleased(RobotMap.rangeLowerButton);
  }

  public static boolean getRangeButton2Pressed() {
    return left.getRawButtonReleased(RobotMap.rangeRaiseButton);
  }*/

  public static double getRotation() {
    rotation = 0.0;
    if(Math.abs(right.getZ()) > 0.3) {
      rotation += right.getZ() * r_joystick_sens;
    }
    /*if(Math.abs(left.getZ()) > 0.1) {
      rotation += left.getZ() * l_joystick_sens;
    }*/
		return rotation;
	}
	
	public static double getForward() {
    forward = 0.0;
    if(Math.abs(right.getY()) > 0.3) {
      forward += right.getY() * r_joystick_sens;
    }
    return forward;
  }

  public Joystick getRightStick() {
    return right;
  }

  public Joystick getLeftStick() {
    return left;
  }

  public static boolean getCameraSwitch() {
    return right.getRawButtonReleased(RobotMap.cameraSwitchPort);
  }

  public static double getRollButton() {
    SmartDashboard.putNumber("OI.leftPOV", left.getPOV());
    //return ((left.getPOV() != -1 && (left.getPOV() >= 315 || left.getPOV() <= 45)) ? 1 : 0) - 
    //((left.getPOV() != -1 && (left.getPOV() >= 135 || left.getPOV() <= 225)) ? 1 : 0);
    return left.getPOV() != -1 ? ((double)(Math.min(180, left.getPOV()) - 90) / -90.0) : 0;
  }
  public static double getSlowRollButton() {
    //return ((left.getPOV() != -1 && (left.getPOV() >= 315 || left.getPOV() <= 45)) ? 1 : 0) - 
    //((left.getPOV() != -1 && (left.getPOV() >= 135 || left.getPOV() <= 225)) ? 1 : 0);
    return right.getPOV() != -1 ? ((double)(Math.min(right.getPOV(), 180) - 90) / -90.0) : 0;
  }

  public static boolean getBallAdjustButton() {
    return right.getRawButton(RobotMap.ballAdjustPort);
  }

  public static boolean getTapeAdjustButton() {
    return right.getRawButton(RobotMap.tapeAdjustPort);
  }

  public static boolean getRaiseButton() {
    return right.getRawButton(RobotMap.raisePort);
  }

  public static boolean getLowerButton() {
    return right.getRawButton(RobotMap.lowerPort);
  }

  public static boolean getRaiseButtonPressed() {
    return right.getRawButtonPressed(RobotMap.raisePort);
  }

  public static boolean getLowerButtonPressed() {
    return right.getRawButtonPressed(RobotMap.lowerPort);
  }
  public static boolean getManualRaiseButton() {
    boolean manualraise = right.getRawButton(RobotMap.manualraisePort);
    return manualraise;
  }

  public static boolean getManualLowerButton() {
    boolean manuallower = right.getRawButton(RobotMap.manuallowerPort);
    return manuallower;
  }

  public static boolean getManualRelease() {
    boolean manualrelease = (right.getRawButtonReleased(RobotMap.manualraisePort) && !right.getRawButton(RobotMap.manuallowerPort) 
    || right.getRawButtonReleased(RobotMap.manuallowerPort) && !right.getRawButton(RobotMap.manualraisePort));
    return manualrelease;
  }

  public static int getButtons() 
  {
    int out = 0;
    boolean[] buttons = {left.getRawButtonPressed(RobotMap.greenleft), left.getRawButtonPressed(RobotMap.greenright),
      left.getRawButtonPressed(RobotMap.yellowleft),left.getRawButtonPressed(RobotMap.yellowright), 
      left.getRawButtonPressed(RobotMap.redleft), left.getRawButtonPressed(RobotMap.redright),
      left.getRawButtonPressed(RobotMap.blueleft), left.getRawButtonPressed(RobotMap.blueright),
      left.getRawButtonPressed(RobotMap.white)};
    loop: for(int i = 0; i < buttons.length; i++) {
      if(buttons[i]) {
        out = i + 1;
        break loop;
      }
    }
    return out;
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
