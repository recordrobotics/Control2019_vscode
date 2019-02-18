package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class Test extends Command {
	// Command to test any new motors.
	public Test() {
		requires(Robot.lifter); 
		requires(Robot.acquisition); 
	}
	final static double liftsens = 0.5;
	final static double acquisitionsens = 0.5;
	@Override
	protected void initialize() {
	}
	@Override
	protected void execute() {
		if((OI.getManualRaiseButton() ? 1 : 0) - (OI.getManualLowerButton() ? 1 : 0) != 0) {
			Robot.lifter.setLift(liftsens*(OI.getManualRaiseButton() ? 1 : 0));
			Robot.lifter.setLift(-liftsens*(OI.getManualLowerButton() ? 1 : 0));
			SmartDashboard.putNumber("LiftEncoder", Robot.lifter.getlifterpos());
		}
		if((OI.getRaiseButton() ? 1 : 0) - (OI.getLowerButton() ? 1 : 0) != 0) {
			Robot.acquisition.rotate(acquisitionsens*(OI.getManualRaiseButton() ? 1 : 0));
			Robot.lifter.setLift(-acquisitionsens*(OI.getManualLowerButton() ? 1 : 0));
			SmartDashboard.putNumber("AcquisitionEncoder", Robot.acquisition.getacquisitionpos());
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
