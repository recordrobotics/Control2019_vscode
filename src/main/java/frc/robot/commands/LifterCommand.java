package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class LifterCommand extends Command {
	// Toggled button to switch from automatic or manual lift. Not currently used because automatic and manual lift use different buttons.
	boolean manualLift = false;
	// Magnetic switches
	boolean switch0 = false;
	boolean switch1 = false;
	boolean switch2 = false;
	double lifterpos = Robot.lifter.getlifterpos();
	/*
	Buttons for raising and lowering lift using PID loop vs Manual code. 
	Auto is currently press while Manual is hold. Both raise/lower to the next available position. 
	How should manual and auto act differently?
	*/
	boolean autoraise = false;
	boolean autolower = false;
	boolean manualraise = false;
	boolean manuallower = false;
	// Used to smooth encoder values so that the lift doesn't have to be at an exact value. Not really used to effect.
	final static double stateSmooth = 0.2;

	int reset = 0;
	double movement = 0;
	public LifterCommand() {
		requires(Robot.lifter);
	}
	@Override
	protected void initialize() {
		Robot.lifter.getPIDController().setEnabled(false);
		Robot.lifter.setLift(0);
		reset = 1;
	}
	@Override
	protected void execute() {
		// Input to the motors will be set at 0 if none of the conditions are met
		movement = 0;
		switch0 = Robot.lifter.get0switch();
		switch1 = Robot.lifter.get1switch();
		switch2 = Robot.lifter.get2switch();
		autoraise = OI.getAutoRaiseButton();
		autolower = OI.getAutoLowerButton();
		manualraise = OI.getManualRaiseButton();
		manuallower = OI.getManualLowerButton();
		lifterpos = Robot.lifter.getlifterpos();
		// Reset the lift back to default position, and reset encoder values if necessary
		if(reset == 1) {
			if(!switch0)
				movement = -1;
			else {
				reset = 0;
				if(lifterpos > 0.3) {
					Robot.lifter.encoderReset();
					lifterpos = Robot.lifter.getlifterpos();
				}
			}			
		}

		if (lifterpos - Math.floor(lifterpos) < stateSmooth) {
			lifterpos = Math.floor(lifterpos);
		}
		//Auto automatically raises/lowers to the next available standard position
		if(autoraise || autolower && !(autoraise && autolower)) { // Either but not both raise and lower must be activated
			// To raise the lift cannot be at the top position
			if(autoraise && lifterpos < 1.7 && !switch2) { 
				// The PID controller should move to position 1 if at 0,s or 2 if at 1. Actual values will be different.
				if(lifterpos > 0.8 && switch2)
					Robot.lifter.setSetPoint(2.0);
				else if(lifterpos >= 0) 
					Robot.lifter.setSetPoint(1.0);
				Robot.lifter.getPIDController().setEnabled(true);
			}
			// To lower the lift cannot be at the bottom position
			else if(autolower && lifterpos > 0.3 && !switch0) {
				// The PID controller should move to position 1 if at 2 or 0 if at 1. Actual values will be different.
				if(lifterpos < 1.2 && switch1)
					Robot.lifter.setSetPoint(0.0);
				else if(lifterpos <= 2 && switch2) 
					Robot.lifter.setSetPoint(1.0);
				Robot.lifter.getPIDController().setEnabled(true);
			}
			else
				Robot.lifter.getPIDController().setEnabled(false);
		}
		// Manual does not use stages
		else if(manualraise || manuallower && !(manualraise && manuallower)) {
			Robot.lifter.getPIDController().setEnabled(false);
			if(autoraise && lifterpos < 1.7 && !switch2) {
				//if(lifterpos > 0.8 && !switch2)
				//	movement = 1;
				//else if(lifterpos >= 0 && !switch1) 
					movement = 1;
			}
			else if(autolower && lifterpos > 0.3) {
				if(lifterpos < 1.2 && !switch0)
					movement = -1;
				else if(lifterpos <= 2 && !switch1) 
					movement = -1;
			}
			Robot.lifter.setLift(movement);
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
		Robot.lifter.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.lifter.stop();
	}
}
