/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class ManualDrive extends Command {
	public ManualDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drivetrain);
		requires(Robot.newdrivetrain);
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.drivetrain.stop();
	}

	double forward;
	double rotation;
	double joyforw;
	double joyrot;
	double nextrotation;
	double nextforward;
	double counter = 0;
	double counter1 = 0;
	final double updaterate = 0.05;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		joyforw = OI.getForward();
		joyrot = OI.getRotation();
		if (counter == 0 || Math.abs(joyforw) < Math.abs(nextforward)) {
			forward = joyforw/2;
			counter++;
		}
		else if(joyforw < 0 && nextforward - updaterate >= joyforw)
			forward = nextforward - updaterate;
		else if(nextforward + updaterate <= joyforw)
			forward = nextforward + updaterate;
		else
			forward = joyforw;
			/*
		if (counter1 == 0 || Math.abs(joyrot) < Math.abs(nextrotation)) {
			rotation = joyrot/2;
			counter++;
		}
		else if(joyrot < 0 && nextrotation - updaterate >= joyrot)
			rotation = nextrotation - updaterate;
		else if(nextrotation + updaterate <= joyrot)
			rotation = nextrotation + updaterate;
		else
			rotation = joyrot;
			*/
		Robot.drivetrain.curvatureDrive(forward, OI.getRotation());
		Robot.newdrivetrain.curvatureDrive(OI.getForward(), OI.getRotation());
		nextforward = forward;
		//nextrotation = rotation;
		
		//Robot.drivetrain.curvatureDrive(OI.getForward(), OI.getRotation());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.drivetrain.stop();
	}
}
