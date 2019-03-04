package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class LifterCommand extends Command {
	// Toggled button to switch from automatic or manual lift. Not currently used because automatic and manual lift use different buttons.
	// Magnetic switches
	boolean switch0;
	boolean top_switch;
	// Standard lift positions
	double[] auto_positions;
	// Joystick inputs
	boolean autoraise;
	boolean autolower;
	boolean manualraise;
	boolean manuallower;
	boolean manualrelease;
	//final static double manualupdaterate = 0.01;

	int reset;
	double movement;
	int auto_position_index = 0;

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
		top_switch = Robot.lifter.get1switch() && Robot.lifter.get2switch();
		autoraise = OI.getAutoRaiseButton();
		autolower = OI.getAutoLowerButton();
		manualraise = OI.getManualRaiseButton();
		manuallower = OI.getManualLowerButton();
		manualrelease = OI.getManualRelease();
		auto_positions = Robot.lifter.getAutoPositions();
		// Reset the lift back to default position, and reset encoder values if necessary
		if(reset == 1) {
			if(!switch0) {
				movement = -1.0;
				Robot.lifter.setLift(movement);
				System.out.println("down");
			}
			else {
				reset = 0;
				Robot.lifter.encoderReset();
				Robot.lifter.setLift(0.0);
				//Robot.lifter.getPIDController().setEnabled(true);
				Robot.lifter.setSetpoint(0.0);
			}			
		}
		else if(switch0) {
			Robot.lifter.encoderReset();
		}

		//Auto automatically raises/lowers to the next available standard position
		if(reset == 0) {
			if((autoraise || autolower) && !(autoraise && autolower)) { // Either but not both raise and lower must be activated	
				System.out.println("Auto called");
				// To raise the lift cannot be at the top position
				if(autoraise && !top_switch) { 
					// The PID controller should move to position 1 if at 0,s or 2 if at 1. Actual values will be different.
					if(auto_position_index < (auto_positions.length - 1)) {
						auto_position_index++;
						Robot.lifter.setSetPoint(auto_positions[auto_position_index]);
					}
				}
				// To lower the lift cannot be at the bottom position
				else if(autolower && !switch0) {
					// The PID controller should move to position 1 if at 2 or 0 if at 1. Actual values will be different.
					if(auto_position_index > 0) {
						auto_position_index--;
						Robot.lifter.setSetPoint(auto_positions[auto_position_index]);
					}
				}
			}
			// Manual does not use stages
			if((manualraise || manuallower) && !(manualraise && manuallower)) {
				Robot.lifter.getPIDController().setEnabled(false);
				if(manualraise && !top_switch) {
					movement = 1;
				}
				else if(manuallower && !switch0) {
					movement = -1;
				}
				Robot.lifter.setLift(movement);
			}
			if (manualrelease) {
				Robot.lifter.setLift(0.0);
				Robot.lifter.getPIDController().setEnabled(true);
				Robot.lifter.setSetPoint(Robot.lifter.getlifterpos());
			}
		}
		//Robot.lifter.setSetpoint(Robot.lifter.getSetPoint() + manualupdaterate*movement);
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
