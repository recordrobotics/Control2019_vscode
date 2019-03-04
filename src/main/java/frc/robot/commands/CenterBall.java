package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class CenterBall {
  private long timeout, start_time;
  private double error_x, error_y;

  final private double zero_x = 0.4;
  final private double zero_y = 0.5;
  final private double threshold_x = 0.02;
  final private double threshold_y = 0.02;
  final private double move_x = 0.1;
  final private double move_y = 0.1;

  public CenterBall(long t) {
    timeout = t;
    start_time = 0;
    error_x = 0.0;
    error_y = 0.0;
  }

  @Override
  protected void initialize() {
    Robot.newdrivetrain.stop();
    start_time = System.getTimeMillis();
    error_x = 0.0;
    error_y = 0.0;
  }

  @Override
  protected void execute() {
    double ball_x = SmartDashboard.getNumber("ball_x|PI_2", -2.0);
    double ball_y = SmartDashboard.getNumber("ball_y|PI_2", -2.0);
    
    if(ball_x < -1.5) {
      error_x = 0.0;
    } else {
      error_x = ball_x - zero_x;
    }
    
    if(ball_y < -1.5) {
      error_y = 0.0;
    } else {
      error_y = ball_y - zero_y;
    }

    double left = 0.0;
    double right = 0.0;

    if(Math.abs(error_x) > threshold_x) {
      double x = Math.signum(error_x) * move_x;
      left += x;
      right -= x;
    }
    
    if(Math.abs(error_y) > threshold_y) {
      double y = Math.signum(error_y) * move_y;
      left -= y;
      right -= y;
    }

    Robot.newdrivetrain.drive(left, right);
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Math.abs(error_x) <= threshold_x && Math.abs(error_y) <= threshold_y) ||
           (System.getTimeMillis() - start_time > timeout);
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
