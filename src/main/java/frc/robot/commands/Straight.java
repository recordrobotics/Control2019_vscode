package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class Straight extends Command {
  public enum Finish {
    PASS, THRESHOLD
  }

  private long timeout, start_time;
  private double angle, start_angle, last_l, last_r, start_dist_l, start_dist_r, threshold, avg_dist, final_dist;
  private boolean useGyro;
  Finish condition;

  private final double turn_sens = 0.002;
  private final double max_turn_speed = 0.2;

  private final double forward_sens = 2.0;
  private final double max_speed = 0.4;

  public Straight(double d, boolean gyro, long t, Finish cond, double thresh) {
    timeout = t;
    useGyro = gyro;
    final_dist = d;
    angle = 0.0;
    start_angle = 0.0;
    start_time = 0;
    last_l = 0.0;
    last_r = 0.0;
    start_dist_l = 0.0;
    start_dist_r = 0.0;
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
    Robot.newdrivetrain.stop();
    avg_dist = 0.0;
    start_dist_l = Robot.newdrivetrain.getleftdistance();
    start_dist_r = Robot.newdrivetrain.getrightdistance();
  }

  @Override
  protected void execute() {
    if(useGyro) {
      angle = Robot.gyro.getAngle();
    }
    else {
      double l = Robot.newdrivetrain.getleftdistance();
      double r = Robot.newdrivetrain.getrightdistance();
      
      double dl = l - last_l;
      double dr = r - last_r;
      
      last_l = l;
      last_r = r;
      
      angle += (dl - dr)/Robot.drivetrainWidth;
    }

    avg_dist = 0.5 * ((Robot.newdrivetrain.getleftdistance() - start_dist_l) + (Robot.newdrivetrain.getrightdistance() - start_dist_r));

    double turn = Robot.clamp(turn_sens * (angle - start_angle), -max_turn_speed, max_turn_speed);
    double forward = Robot.clamp(forward_sens * (final_dist - avg_dist), -max_speed, max_speed);
    
    Robot.newdrivetrain.drive(forward + turn, forward - turn);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if((System.currentTimeMillis() - start_time > timeout) || 
       (condition == Finish.PASS && (final_dist >= 0 && avg_dist >= final_dist || final_dist < 0 && avg_dist <= final_dist)) ||
       (condition == Finish.THRESHOLD && Math.abs(avg_dist - final_dist) < threshold)) {
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
