package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class Test extends Command {
	// Command to test any new motors.
	public Test() {
		requires(Robot.lifter); 
	}
	@Override
	protected void initialize() {
	}
	@Override
	protected void execute() {
		Robot.lifter.setLift(OI.getForward());
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
