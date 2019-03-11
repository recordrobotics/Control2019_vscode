package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class SetDrive extends Command {
  private double left, right;
  long time, start_time;

  public SetDrive(double l, double r, long t) {
    left = l;
    right = r;
    time = t;
    requires(Robot.newdrivetrain);
  }
  
  @Override
  protected void initialize() {
    Robot.newdrivetrain.drive(left, right);
    start_time = System.currentTimeMillis();
  }

  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (System.currentTimeMillis() - start_time > time);
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
