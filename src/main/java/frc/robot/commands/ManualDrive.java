package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualDrive extends Command {
	public ManualDrive() {
		requires(Robot.drivetrain);
	}

	long[] f_start_time = {0};
	long[] r_start_time = {0};

	final long f_warmup = 500;
	final long r_warmup = 500;
	final double f_sens = 0.5;
	final double r_sens = 0.25;
	final double r_sens_fast = 0.5;
	final double f_pow = 1.0;
	final double r_pow = 1.0;

	@Override
	protected void initialize() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void execute() {
		double fs = f_sens;
		if(OI.boost()) {
			fs = 1.0;
		}

		double of = OI.getForward();
		double forward = Robot.smoothAccel(of, f_start_time, f_warmup, fs, f_pow);

		double rs = r_sens;
		if(OI.tBoost()) {
			rs = 1.0;
		} else if(Math.abs(forward) <= 0.3) {
			rs = r_sens_fast;
		}
		
		double or = OI.getRotation();
		double rotation = Robot.smoothAccel(or, r_start_time, r_warmup, rs, r_pow);
		
		SmartDashboard.putNumber("Joystick forward", of);
		SmartDashboard.putNumber("Joystick rotation", or);
		SmartDashboard.putNumber("Wheels forward", forward);
		SmartDashboard.putNumber("Wheels rotation", rotation);

		Robot.drivetrain.curvatureDrive(forward, rotation);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.drivetrain.stop();
	}
}
