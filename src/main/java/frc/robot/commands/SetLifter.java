package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class SetLifter extends Command {
  private double val;

  public SetLifter(double v) {
    val = v;
    requires(Robot.lifter);
  }
  
  public SetLifter(int pos) {
    val = Robot.lifter.auto_positions[pos];
    requires(Robot.lifter);
  }

  @Override
  protected void initialize() {
    Robot.lifter.getPIDController().setEnabled(true);
    Robot.lifter.setSetpoint(val);
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
    Robot.lifter.setSetpoint(Robot.lifter.getlifterpos());
  }
}
