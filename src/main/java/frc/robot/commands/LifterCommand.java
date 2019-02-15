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
public class LifterCommand extends Command {
    boolean raisebutton = OI.getLiftRaiseButton();
    boolean lowerbutton = OI.getLiftLowerButton();
	int liftstate = OI.getswitchLiftControl();
	int toggler = 0;
	public LifterCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.lifter); 
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.lifter.getPIDController().setEnabled(false);
		Robot.lifter.setLift(0.0);
	}
	

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        if(liftstate == 0 && toggler == 0) {
			
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
		Robot.acquisition.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.acquisition.stop();
	}
}
