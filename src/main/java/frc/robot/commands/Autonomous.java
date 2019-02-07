/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;


/**
 * An example command.  You can replace me with your own command.
 */
public class Autonomous extends Command {
  public Autonomous() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double base_speed = 0.2;                                                                                                  
    double turn_factor = -0.4;
    double line_error = SmartDashboard.getNumber("tapes", -2.0);
    double turn_clamp = 0.1;
    if(line_error > -1.5)
    {
      Robot.drivetrain.curvatureDrive(-base_speed, -Math.max(-turn_clamp, Math.min(turn_clamp, line_error*turn_factor)));
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
