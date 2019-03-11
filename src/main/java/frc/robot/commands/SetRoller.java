package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class SetRoller extends Command {
  private double val;
  long time, start_time;

  public SetRoller(double v, long t) {
    val = v;
    time = t;
    requires(Robot.acquisition);
  }
  
  @Override
  protected void initialize() {
    Robot.acquisition.roll(val);
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
    Robot.acquisition.roll(0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.acquisition.roll(0.0);
  }
}
