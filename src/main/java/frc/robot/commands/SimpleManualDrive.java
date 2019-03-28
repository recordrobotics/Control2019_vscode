package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class SimpleManualDrive extends Command {
	public SimpleManualDrive() {
		requires(Robot.newdrivetrain);
	}

	@Override
	protected void initialize() {
		Robot.newdrivetrain.stop();
	}

	@Override
	protected void execute() {
		Robot.newdrivetrain.curvatureDrive(OI.getForward(), OI.getRotation());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.newdrivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.newdrivetrain.stop();
	}
}
