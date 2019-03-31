package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualDrive extends Command {
	public ManualDrive() {
		//requires(Robot.drivetrain);
		requires(Robot.newdrivetrain);
	}

	long[] f_start_time = {0};
	long[] r_start_time = {0};

	final long f_warmup = 500;
	final long r_warmup = 500;
	final double f_sens = 0.5;
	final double r_sens = 0.25;
	final double f_pow = 1.0;
	final double r_pow = 1.0;

	final double b_sens = 1.5;
	final double b_max = 0.17;

	@Override
	protected void initialize() {
		Robot.newdrivetrain.stop();
	}

	@Override
	protected void execute() {
		double forward = Robot.smoothAccel(OI.getForward(), f_start_time, f_warmup, f_sens, f_pow);
		double rotation = 0.0;
		if(Math.abs(forward) > 0.3)
			rotation = Robot.smoothAccel(OI.getRotation(), r_start_time, r_warmup, r_sens, r_pow);
		else
			rotation = Robot.smoothAccel(OI.getRotation(), r_start_time, r_warmup, r_sens + 0.3, r_pow);

		if(OI.boost()) {
			forward *= 2.0;
		}

		if(OI.tBoost()) {
			rotation *= 4.0;
		}

		Robot.newdrivetrain.curvatureDrive(forward, rotation);
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
