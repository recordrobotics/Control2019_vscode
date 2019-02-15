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
	boolean manualLift = OI.getswitchLiftControl();
	boolean liftswitch = Robot.lifter.getSwitch(); 
	double lifterpos = Robot.lifter.getlifterpos();
	int toggler = 0;
	double movement = 0;
	public LifterCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.lifter); 
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.lifter.getPIDController().setEnabled(false);
		Robot.lifter.setLift(0);
	}
	

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		movement = 0;
		int raisebutton = OI.getLiftRaiseButton() ? 1 : 0;
    	int lowerbutton = OI.getLiftLowerButton() ? 1 : 0;
		manualLift = OI.getswitchLiftControl();
        if((toggler == 0 || toggler == 1) && manualLift) {
			Robot.lifter.getPIDController().setEnabled(false);
			toggler = 2;
		}
		if((toggler == 0 || toggler == 2) && !manualLift) {
			Robot.lifter.getPIDController().setEnabled(true);
			toggler = 1;
		}
		if(manualLift) {
			if(raisebutton*lowerbutton != -1) // Raisebutton and lower button are not both pushed
			{ 
			if(liftswitch) {
				if(lifterpos < 0.2) {
					raisebutton = 0;
				}
				if(lifterpos > 1.8) {
					lowerbutton = 0;
				}
			}
			Robot.lifter.setLift(raisebutton + lowerbutton);
			}

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
