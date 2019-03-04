package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class SetRoller extends Command {
  private double val;

  public SetRoller(double v) {
    val = v;
    requires(Robot.acquisition);
  }
  
  @Override
  protected void initialize() {
    Robot.acquisition.roll(val);
  }

  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.acquisition.roll(0.0);
  }
}
