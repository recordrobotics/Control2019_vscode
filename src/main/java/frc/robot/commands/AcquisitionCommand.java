/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class AcquisitionCommand extends Command {
	public AcquisitionCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.acquisition);
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.acquisition.stop();
	}
	boolean rollbutton = OI.getRollButton();
	int raisebutton = OI.getRaiseButton() ? 1 : 0;
	int lowerbutton = -1 * (OI.getLowerButton() ? 1 : 0);
	boolean magnetswitch = Robot.acquisition.getMagneticSwitch(); 
	double state = 0;
	final static double rollerSpeed = 0.5;
	final static double acquisitionSpeed = 0.5;
	final static double stateSmooth = 0.2;
	double acquisitionpos = 0;
	double movement = 0;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		movement = 0;
		acquisitionpos = Robot.getacquisitionpos();
		if (acquisitionpos % 1 < stateSmooth)
			acquisitionpos -= acquisitionpos % 1;
		state = Math.abs(acquisitionpos);
		if(rollbutton)
			Robot.acquisition.roll(rollerSpeed);
		else if(magnetswitch && Math.abs(state) > 0)
			movement = 0;
		else if(raisebutton*lowerbutton != -1 && raisebutton + lowerbutton != 0) {
			//if(acquisitionpos )
		}
		Robot.acquisition.rotate(movement);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.acquisition.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.acquisition.stop();
	}
}
