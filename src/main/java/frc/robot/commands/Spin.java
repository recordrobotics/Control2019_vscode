package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class Spin extends Command {
  public enum Finish {
    PASS, THRESHOLD
  }

  private long timeout, start_time;
  private double final_angle, angle, start_angle, d_angle, last_l, last_r, threshold;
  private boolean useGyro;
  Finish condition;

  private final double sens = 0.01;
  private final double max_speed = 0.4;

  public Spin(double ang, boolean gyro, long t, Finish cond, double thresh) {
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
    condition = cond;

    requires(Robot.newdrivetrain);
  }
  
  @Override
  protected void initialize() {
    start_time = System.currentTimeMillis();
    if(useGyro) {
      start_angle = Robot.gyro.getAngle();
    } else {
      start_angle = 0.0;
      last_l = Robot.newdrivetrain.getleftdistance();
      last_r = Robot.newdrivetrain.getrightdistance();
    }
    angle = start_angle;
    final_angle = start_angle + d_angle;
  }

  @Override
  protected void execute() {
    if(useGyro) {
      angle = Robot.gyro.getAngle();
    } else {
      double l = Robot.newdrivetrain.getleftdistance();
      double r = Robot.newdrivetrain.getrightdistance();
     
      double dl = l - last_l;
      double dr = r - last_r;
      
      last_l = l;
      last_r = r;
      
      angle += (dl - dr)/Robot.drivetrainWidth;
    }

    double f = Robot.clamp(sens * (angle - final_angle), -max_speed, max_speed);
    Robot.newdrivetrain.drive(f, -f);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((System.currentTimeMillis() - start_time > timeout) || 
       (condition == Finish.PASS && (start_angle < final_angle && angle >= final_angle || start_angle > final_angle && start_angle <= final_angle)) ||
       (condition == Finish.THRESHOLD && Math.abs(angle - final_angle) < threshold)) {
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.newdrivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.newdrivetrain.stop();
  }
}
