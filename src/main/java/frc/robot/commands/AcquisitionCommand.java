package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class AcquisitionCommand extends Command {
	public AcquisitionCommand() {
		requires(Robot.acquisition);
	}
	@Override
	protected void initialize() {
		Robot.acquisition.stop();
	}
	int rollbutton = OI.getRollButton();
	int raisebutton = OI.getRaiseButton() ? 1 : 0;
	int lowerbutton = -1 * (OI.getLowerButton() ? 1 : 0);
	boolean magnetswitch = Robot.acquisition.getSwitch(); 
	// Double representing the position of the acquisition, of 3. Not currently used
	double state = 0;
	final static double rollerSpeed = 0.5;
	final static double acquisitionSpeed = 0.5;
	// Used to smooth encoder values so that the acquisition doesn't have to be at an exact value. Not really used to effect.
	final static double stateSmooth = 0.2;
	double acquisitionpos = 0;
	double movement = 0;

	@Override
	protected void execute() {
		movement = 0;
		acquisitionpos = Robot.acquisition.getacquisitionpos();
		if (acquisitionpos - Math.floor(acquisitionpos) < stateSmooth) {
			acquisitionpos = Math.floor(acquisitionpos);
		}
		// Can roll in two directions based on which roll button is pressed
		Robot.acquisition.roll(rollerSpeed*rollbutton);
		if(raisebutton*lowerbutton != -1) // Raise button and lower button are not pushed at the same time
		{ 
			if(magnetswitch) { // If we encounter a magnet switch. Currently has the issue of being active at our starting position.
				if(acquisitionpos < 0.2) {
					raisebutton = 0;
				}
				if(acquisitionpos > 1.8) {
					lowerbutton = 0;
				}
			}
			movement = (raisebutton + lowerbutton)*acquisitionSpeed; // Either 1, -1 or 0
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
