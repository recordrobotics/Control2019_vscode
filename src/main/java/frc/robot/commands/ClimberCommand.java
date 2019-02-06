/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.OI;
import frc.robot.Robot;


/**
 * An example command.  You can replace me with your own command.
 */
public class ClimberCommand extends Command {
  public ClimberCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.climb);
  }

  int rightbutton = OI.getRightClimbButton() ? 1 : 0;
  int leftbutton = OI.getLeftClimbButton() ? 1 : 0;
  private final double climberMoveSpeed = 1;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.climb.stopMotor();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double power = climberMoveSpeed * (double)(rightbutton - leftbutton);

    if(power < 0 && !Robot.climb.getBottomSwitch() || power > 0 && !Robot.climb.getTopSwitch())
      power = 0;
    Robot.climb.setMotor(power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climb.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
