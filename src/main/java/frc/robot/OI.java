package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.Reset;
import frc.robot.commands.SimpleAcquisitionCommand;
import frc.robot.commands.SimpleLifterCommand;
import frc.robot.commands.SimpleManualDrive;
import frc.robot.commands.CenterTape;
import frc.robot.commands.NetworkCommand;
import frc.robot.commands.PIDSpin;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick right = new Joystick(RobotMap.rightjoyPort);
  public static Joystick left = new Joystick(RobotMap.leftjoyPort); // Button Panel

	private static double forward;
  private static double rotation;
  private static double speed = 1.0;

  private static final double r_joystick_sens = 1.0;
  private static double joystick_slider_sens = 0.0;
  //for Alex set at l = 0.35 and r = 1
  

  public OI() {
    JoystickButton but = new JoystickButton(left, RobotMap.white);
    but.whenPressed(new Reset(10000));
   /* JoystickButton simpleAcquisition = new JoystickButton(right, 9);
    simpleAcquisition.toggleWhenPressed(new SimpleAcquisitionCommand());
    JoystickButton simpleLift = new JoystickButton(right, 11);
    simpleLift.toggleWhenPressed(new SimpleLifterCommand());
    JoystickButton simpleDrive = new JoystickButton(right, 7);
    simpleDrive.toggleWhenPressed(new SimpleManualDrive());
*/
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

  public static double getOldLifter() {
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

  public static double getSens() {
    return right.getThrottle() * -0.5 + 0.5;
  }

  public static boolean boost() {
    return right.getRawButton(RobotMap.boostButton);
  }

  public static boolean tBoost() {
    return right.getRawButton(RobotMap.tBoostButton);
  }

  public static double getRotation() {
    rotation = 0.0;
    joystick_slider_sens = getSens(); //right.getThrottle() * -0.5 + 0.5;

    if(Math.abs(right.getZ()) > 0.3) {
      rotation += right.getZ() * joystick_slider_sens;
    }
    /*if(Math.abs(left.getZ()) > 0.1) {
      rotation += left.getZ() * l_joystick_sens;
    }*/
		return rotation;
	}
	
	public static double getForward() {
    forward = 0.0;
    joystick_slider_sens = getSens(); //right.getThrottle() * -0.5 + 0.5;
    if(Math.abs(right.getY()) > 0.3) {
      forward += right.getY() * joystick_slider_sens;
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
    return left.getRawButtonPressed(RobotMap.blueleft);
  }

  public static double getRollButton() {
    //SmartDashboard.putNumber("OI.leftPOV", left.getPOV());
    //return ((left.getPOV() != -1 && (left.getPOV() >= 315 || left.getPOV() <= 45)) ? 1 : 0) - 
    //((left.getPOV() != -1 && (left.getPOV() >= 135 || left.getPOV() <= 225)) ? 1 : 0);
    int pov = right.getPOV();
    double v = 0.0;
    if(pov == 0) {
      v = 1.0;
    } else if(pov == 180) {
      v = -1.0;
    }
    return v;
  }
  public static double getSlowRollButton() {
    //return ((left.getPOV() != -1 && (left.getPOV() >= 315 || left.getPOV() <= 45)) ? 1 : 0) - 
    //((left.getPOV() != -1 && (left.getPOV() >= 135 || left.getPOV() <= 225)) ? 1 : 0);
   return 0.0;
  }

  public static boolean getPieceAdjustButton() {
    return right.getRawButton(RobotMap.pieceAdjustPort);
  }

  public static boolean getTapeAdjustButton() {
    return right.getRawButton(RobotMap.tapeAdjustPort);
  }

  /*public static boolean getPieceAdjustPressed() {
    return right.getRawButtonPressed(RobotMap.pieceAdjustPort);
  }

  public static boolean getPieceAdjustReleased() {
    return right.getRawButtonReleased(RobotMap.pieceAdjustPort);
  }

  public static boolean getTapeAdjustButton() {
    return right.getRawButton(RobotMap.tapeAdjustPort);
  }

  public static boolean getTapeAdjustReleased() {
    return right.getRawButtonReleased(RobotMap.tapeAdjustPort);
  }

  public static boolean getTapeAdjustPressed() {
    return right.getRawButtonPressed(RobotMap.tapeAdjustPort);
  }*/

  public static boolean getPivotRaiseButton() {
    return right.getRawButton(RobotMap.pivotraisePort);
  }

  public static boolean getPivotLowerButton() {
    return right.getRawButton(RobotMap.pivotlowerPort);
  }

  public static boolean getPivotRaiseButtonPressed() {
    return right.getRawButtonPressed(RobotMap.pivotraisePort);
  }

  public static boolean getPivotLowerButtonPressed() {
    return right.getRawButtonPressed(RobotMap.pivotlowerPort);
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
    boolean[] buttons = {left.getRawButton(RobotMap.greenleft), left.getRawButton(RobotMap.greenright),
      left.getRawButton(RobotMap.yellowleft),left.getRawButton(RobotMap.yellowright), 
      left.getRawButton(RobotMap.redleft), left.getRawButton(RobotMap.redright),
      left.getRawButton(RobotMap.blueleft), left.getRawButton(RobotMap.blueright),
      left.getRawButton(RobotMap.white)};
    //System.out.println(buttons[0]);
      for(int i = 0; i < buttons.length; i++) {
      if(buttons[i]) {
        out = i + 1;
        break;
      }
    }
    return out;
  } 

   public static boolean getRangeButtonPressed() {
    return right.getRawButtonReleased(RobotMap.rangeLowerButton);
  }
  public static boolean getRangeButton2Pressed() {
    return right.getRawButtonReleased(RobotMap.rangeRaiseButton);
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
