package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class LifterCommand extends Command {
	// Toggled button to switch from automatic or manual lift. Not currently used because automatic and manual lift use different buttons.
	boolean manualLift = false;
	// Magnetic switches
	boolean switch0 = false;
	boolean top_switch = false;
	/*
	Buttons for raising and lowering lift using PID loop vs Manual code. 
	Auto is currently press while Manual is hold. Both raise/lower to the next available position. 
	*/
	boolean autoraise = false;
	boolean autolower = false;
	boolean manualraise = false;
	boolean manuallower = false;
	boolean manualrelease = false;
	//final static double manualupdaterate = 0.01;

	int reset = 0;
	double movement = 0;


	final double[] auto_positions = {0.0, 0.10471428, 0.971, 1.171, 1.9462857, 2.0957};
	int auto_position_index = 0;

	public LifterCommand() {
		requires(Robot.lifter);
	}
	@Override
	protected void initialize() {
		Robot.lifter.getPIDController().setEnabled(false);
		Robot.lifter.setLift(0);
		reset = 1;

		// REMOVE LATER
		/*Robot.lifter.encoderReset();
		Robot.lifter.setLift(0.0);
		Robot.lifter.getPIDController().setEnabled(true);
		Robot.lifter.setSetpoint(0.0);*/
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
					/*if(lifterpos > 0.8 && switch2)
						Robot.lifter.setSetPoint(2.0);
					else if(lifterpos >= 0) 
						Robot.lifter.setSetPoint(1.0);
					Robot.lifter.getPIDController().setEnabled(true);*/
				}
				// To lower the lift cannot be at the bottom position
				else if(autolower && !switch0) {
					// The PID controller should move to position 1 if at 2 or 0 if at 1. Actual values will be different.
					if(auto_position_index > 0) {
						auto_position_index--;
						Robot.lifter.setSetPoint(auto_positions[auto_position_index]);
					}
					/*if(lifterpos < 1.2 && switch1)
						Robot.lifter.setSetPoint(0.0);
					else if(lifterpos <= 2 && switch2) 
						Robot.lifter.setSetPoint(1.0);
					Robot.lifter.getPIDController().setEnabled(true);*/
				}
			}
			// Manual does not use stages
			if((manualraise || manuallower) && !(manualraise && manuallower)) {
				Robot.lifter.getPIDController().setEnabled(false);
				if(manualraise && !top_switch) {
					//if(lifterpos > 0.8 && !switch2)
					//	movement = 1;
					//else if(lifterpos >= 0 && !switch1) 
					movement = 1;
				}
				else if(manuallower && !switch0) {
					//if(!switch0)
					//	movement = -1;
					//else if(lifterpos <= 2 && !switch1) 
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
			//System.out.println(Robot.lifter.getSetpoint());
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
