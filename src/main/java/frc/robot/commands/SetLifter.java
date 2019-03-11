package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class SetLifter extends Command {
  private double val;
  long time, start_time;

  public SetLifter(double v, long t) {
    val = v;
    time = t;
    requires(Robot.lifter);
  }
  
  public SetLifter(int pos, long t) {
    val = Robot.lifter.auto_positions[pos];
    time = t;
    requires(Robot.lifter);
  }

  @Override
  protected void initialize() {
    Robot.lifter.getPIDController().setEnabled(true);
    Robot.lifter.setSetpoint(val);
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
    Robot.lifter.setSetpoint(Robot.lifter.getlifterpos());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.lifter.setSetpoint(Robot.lifter.getlifterpos());
  }
}
