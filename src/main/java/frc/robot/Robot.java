package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Relay;
import frc.robot.commands.NetworkCommand;
import frc.robot.commands.Autonomous;
//import frc.robot.subsystems.Wheels;
import frc.robot.subsystems.NewWheels;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Lifter;
import frc.robot.Network;
import java.io.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
// import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;
  //public static ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
  // public static Wheels drivetrain = new Wheels();
  // Subsystems
  public static NewWheels newdrivetrain = new NewWheels();
  public static Acquisition acquisition = new Acquisition();
  public static Lifter lifter = new Lifter();
  public static Network move_net = new Network(new File("/home/lvuser/data/data.text"));
  public static boolean drive_enable = true;
  public static boolean lifter_enable = true;
  public static boolean test_enable = false;
  public static boolean acquisition_enable = true;

  public static AHRS gyro;
  private static boolean gyroSuccess = false;
  public static Relay led = new Relay(RobotMap.relayPort, Relay.Direction.kForward);

  public static boolean isCalibrating() {
    return gyroSuccess && gyro.isCalibrating();
  }
  
 
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  public static void setLight(boolean turnOn) {
    if(turnOn)
      led.set(Relay.Value.kOn);
    else
      led.set(Relay.Value.kOff);
  }
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    setLight(true);
    
    try {
      gyro = new AHRS(I2C.Port.kOnboard, (byte)200);
      gyroSuccess = true;
    } catch (RuntimeException ex) {
      System.out.println("Error instantiating navX MXP:  " + ex.getMessage());
    }

    m_chooser.setDefaultOption("Simple Auto", new Autonomous()); //new NetworkCommand(4.0, 8.0, 0));
    m_chooser.addOption("Network Auto", new NetworkCommand(1.0, 1.0, 0.0));
    //SmartDashboard.putNumber("fuck you vassilios", move_net.feed(new double[] {0, -3.0, 0, 0})[1]);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putBoolean("gyro.isCalibrating", isCalibrating());
    SmartDashboard.putNumber("gyro.yaw", gyro.getYaw());
    SmartDashboard.putNumber("drivetrain.left_encoder", newdrivetrain.getleftdistance());
    SmartDashboard.putNumber("drivetrain.right_encoder", newdrivetrain.getrightdistance());
    SmartDashboard.putNumber("acquisition.encoder", acquisition.getacquisitionpos());
    SmartDashboard.putNumber("roller.encoder", acquisition.getrollerpos());
    SmartDashboard.putBoolean("acquisition.switch0", acquisition.getswitch0());
    SmartDashboard.putBoolean("acquisition.switch1", acquisition.getswitch1());
    SmartDashboard.putNumber("lifter.encoder", lifter.getlifterpos());
    SmartDashboard.putBoolean("lifter.switch0", lifter.get0switch());
    SmartDashboard.putBoolean("lifter.switch1", lifter.get1switch());
    SmartDashboard.putBoolean("lifter.switch2", lifter.get2switch());
    setLight(true);
    //System.out.println(acquisition.getSetPoint());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you wan8t to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }

    gyro.reset();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    gyro.reset();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    //System.out.println(gyro.getYaw() + " " + isCalibrating());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    }
}
