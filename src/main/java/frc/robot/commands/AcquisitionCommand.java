package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class AcquisitionCommand extends Command {
	public AcquisitionCommand() {
		requires(Robot.acquisition);
	}
	int rollbutton = 0;
	// Raising lowers encoder values, so must be negative input
	int raisebutton = 0;
	int lowerbutton = 0;
	boolean switch0; 
	boolean switch1;
	// Double representing the position of the acquisition, of 3. Not currently used
	double state = 0;
	final static double rollerSpeed = 0.25;
	final static double upacqsens = 0.4;
	final static double downacqsens = 0.2;
	final static double updaterate = 0.04;
	boolean release;
	double acquisitionpos = 0;
	double movement = 0;
	// Threshold for lift encoder values below which acquisition cannot drop down to bottom state
	final static double dropthreshold = -1e9;
	double liftpos;
	int reset = 0;

	@Override
	protected void initialize() {
		Robot.acquisition.stop();
		Robot.acquisition.getPIDController().setEnabled(false);
		reset = 1;

		// Remove later
		/*Robot.acquisition.encoderReset();
		Robot.acquisition.rotate(0.0);
		Robot.acquisition.getPIDController().setEnabled(true);
		Robot.acquisition.setSetpoint(0.0);*/
	}
	@Override
	protected void execute() {
		movement = 0;
		liftpos = Robot.lifter.getlifterpos();
		acquisitionpos = Robot.acquisition.getacquisitionpos();
		raisebutton = -1*(OI.getRaiseButton() ? 1 : 0);
		lowerbutton = OI.getLowerButton() ? 1 : 0;
		rollbutton = OI.getRollButton();
		switch0 = Robot.acquisition.getswitch0();
		switch1 = Robot.acquisition.getswitch1();
		release = OI.getAcquisitionRelease();
		// Reset the lift back to default position, and reset encoder values if necessary
		
		if(reset == 1) {
			if(!switch0)
				Robot.acquisition.rotate(-0.3);
			else {
				reset = 0;
				Robot.acquisition.encoderReset();
				Robot.acquisition.stop();
				Robot.acquisition.getPIDController().setEnabled(true);
				Robot.acquisition.setSetpoint(0.0);
			}
		}
		else if(switch0) {
			Robot.acquisition.encoderReset();
			acquisitionpos = Robot.acquisition.getacquisitionpos();
		}
		// Can roll in two directions based on which roll button is pressed
		Robot.acquisition.roll(rollerSpeed*rollbutton);
		// Raise button and lower button are not pushed at the same time, and one is active
		if(reset == 0 && raisebutton+lowerbutton != 0)
		{
			//Robot.acquisition.getPIDController().setEnabled(false);
			// Only use raise input if not in state 0
			if(!switch0) {
				movement += raisebutton;
			}
			// Don't drop below state 1 if lift is not raised above drop threshold
			if(acquisitionpos >= 1 && dropthreshold >= liftpos) {
				lowerbutton = 0;
			}
			// Only use lower input if not in state 2
			if(!switch1) {
				movement += lowerbutton;
				
			}// Either 1, -1 or 0
			/*
			double acquisitionsens = 0.0;
			if(movement < 0) {
				acquisitionsens = upacqsens;
				//System.out.println(acquisitionsens);
			}
			else
				acquisitionsens = downacqsens;
			
			//Robot.acquisition.rotate(acquisitionsens*movement);
		}
		if (release) {
			Robot.acquisition.rotate(0.0);
			Robot.acquisition.getPIDController().setEnabled(true);
			Robot.acquisition.setSetPoint(Robot.acquisition.getacquisitionpos());
		}
		*/
		//SmartDashboard.putNumber("acquisition.movement", movement);
		}
		Robot.acquisition.setSetpoint(Robot.acquisition.getSetPoint() + updaterate*movement);
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
