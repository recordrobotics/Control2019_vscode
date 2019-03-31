package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick right = new Joystick(RobotMap.rightjoyPort);
  public static Joystick left = new Joystick(RobotMap.leftjoyPort); // Button Panel

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
    double rotation = 0.0;
    double joystick_slider_sens = getSens(); //right.getThrottle() * -0.5 + 0.5;

    if(Math.abs(right.getZ()) > 0.3) {
      rotation += right.getZ() * joystick_slider_sens;
    }
    /*if(Math.abs(left.getZ()) > 0.1) {
      rotation += left.getZ() * l_joystick_sens;
    }*/
		return rotation;
	}
	
	public static double getForward() {
    double forward = 0.0;
    double joystick_slider_sens = getSens(); //right.getThrottle() * -0.5 + 0.5;
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

  public static int getButtons() 
  {
    int out = 0;
    boolean[] buttons = {left.getRawButton(RobotMap.greenleft), left.getRawButton(RobotMap.greenright),
      left.getRawButton(RobotMap.yellowleft),left.getRawButton(RobotMap.yellowright), 
      left.getRawButton(RobotMap.redleft), left.getRawButton(RobotMap.redright),
      left.getRawButton(RobotMap.blueleft), left.getRawButton(RobotMap.blueright),
      left.getRawButton(RobotMap.white)};

    for(int i = 0; i < buttons.length; i++) {
      if(buttons[i]) {
        out = i + 1;
        break;
      }
    }
    return out;
  }
}
