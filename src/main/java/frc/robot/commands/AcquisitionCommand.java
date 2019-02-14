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
	int rollbutton = OI.getRollButton();
	int raisebutton = OI.getRaiseButton() ? 1 : 0;
	int lowerbutton = -1 * (OI.getLowerButton() ? 1 : 0);
	boolean magnetswitch = Robot.acquisition.getSwitch(); 
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
		
		acquisitionpos = Robot.acquisition.getacquisitionpos();
		if (acquisitionpos - Math.floor(acquisitionpos) < stateSmooth) {
			acquisitionpos = Math.floor(acquisitionpos);
		}
			Robot.acquisition.roll(rollerSpeed*rollbutton);
		if(raisebutton*lowerbutton != -1) // Raisebutton and lower button are not both pushed
		{ 
			if(magnetswitch) {
				if(acquisitionpos < 0.2) {
					raisebutton = 0;
				}
				if(acquisitionpos > 1.8) {
					lowerbutton = 0;
				}
			}
			movement = (raisebutton + lowerbutton)*acquisitionSpeed;
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
