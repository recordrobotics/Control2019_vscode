package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class PIDSpin extends PIDCommand {
  private long timeout, start_time;
  private double final_angle, angle, start_angle, d_angle, last_l, last_r, threshold;
  private boolean useGyro;

  private static final double kP = 0.07;
  private static final double kD = 0.2;
  private static final double kI = 0.005;

  private final double max_speed = 0.5;

  public PIDSpin(double ang, boolean gyro, long t, double thresh) {
    super("PIDSpin", kP, kI, kD, Robot.newdrivetrain);
    timeout = t;
    useGyro = gyro;
    d_angle = ang;
    final_angle = 0.0;
    angle = 0.0;
    start_angle = 0.0;
    start_time = 0;
    last_l = 0.0;
    last_r = 0.0;
    threshold = thresh;

    getPIDController().setInputRange(0.0, 360.0);
		getPIDController().setOutputRange(-max_speed, max_speed);
    getPIDController().setAbsoluteTolerance(threshold);
		getPIDController().setContinuous(true);
		getPIDController().setSetpoint(0.0);
		getPIDController().setEnabled(false);
  }
  
  @Override
  protected void initialize() {
    start_time = System.currentTimeMillis();
    if(useGyro) {
      //start_angle = Robot.gyro.getAngle();
      final_angle = d_angle;
      angle = Robot.gyro.getAngle();
    } else {
      //start_angle = 0.0;
      angle = 0.0;
      final_angle = d_angle;
      last_l = Robot.newdrivetrain.getleftdistance();
      last_r = Robot.newdrivetrain.getrightdistance();
    }
    //angle = start_angle;
    //final_angle = start_angle + d_angle;
    getPIDController().setEnabled(true);
    Robot.newdrivetrain.stop();
  }

  @Override
  protected void execute() {
    getPIDController().setEnabled(true);
  }

  protected double returnPIDInput() {
    if(useGyro) {
      angle = Robot.gyro.getAngle();
    } else {
      double l = Robot.newdrivetrain.getleftdistance();
      double r = Robot.newdrivetrain.getrightdistance();
     
      double dl = l - last_l;
      double dr = r - last_r;
      
      last_l = l;
      last_r = r;
      
      angle += (180.0/Math.PI) * (dl - dr) / Robot.drivetrainWidth;
    }

    return (angle - final_angle);
  }

  protected void usePIDOutput(double output) {
    System.out.println(output);
    Robot.newdrivetrain.pidWrite(output, -output);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (System.currentTimeMillis() - start_time > timeout) || getPIDController().onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    getPIDController().setEnabled(false);
    Robot.newdrivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    getPIDController().setEnabled(false);
    Robot.newdrivetrain.stop();
  }
}
