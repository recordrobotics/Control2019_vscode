/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.OI;
import frc.robot.Robot;

public class LiftingCommand extends Command {
  public LiftingCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.lift);
  }

  int rightbutton = OI.getRightClimbButton() ? 1 : 0;
  int leftbutton = OI.getLeftClimbButton() ? 1 : 0;
  private final double climberMoveSpeed = 1;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.lift.stopMotor();
    Robot.lift.initializeCounter();
    // Robot.lift.initializeEncoder();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double power = climberMoveSpeed * (double)(rightbutton - leftbutton);

    if(power < 0 && !Robot.lift.getInnerSwitch() || power > 0 && !Robot.lift.getOuterSwitch())
      power = 0;
    Robot.lift.setMotor(power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.lift.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
