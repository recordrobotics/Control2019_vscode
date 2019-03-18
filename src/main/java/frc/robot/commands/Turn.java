package frc.robot.commands;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;

public class Turn extends PIDCommand {
	public Turn() {
		super("TurnCommand", Rp, Ri, Rd);
		//reset_vel = rv;
		getPIDController().setAbsoluteTolerance(tolerance);
		getPIDController().setContinuous(false);
		setSetPoint(0);
		getPIDController().setEnabled(false);
		getPIDController().setInputRange(0, 360);
		getPIDController().setOutputRange(-1.0, 1.0);
		requires(Robot.newdrivetrain);
	}
	private static double Rp = 0.0;
	private static double Ri = 0.0;
	private static double Rd = 0.0;
	private final static double tolerance = 0.01;
	@Override
	protected void initialize() {
		Robot.acquisition.roll(0.0);
	}

	@Override
	protected void execute() {
	}

	protected double returnPIDInput() {
    	return (Robot.gyro.getAngle() % 360) + 360*(Robot.gyro.getAngle() < 0 ? 1 : 0);
	}

	protected void usePIDOutput(double output) {
		Robot.newdrivetrain.pidWrite(output, output); // this is where the computed output value fromthe PIDController is applied to the motor
	}

	public double getSetPoint() {
		return getPIDController().getSetpoint();
	}

	public void setSetPoint(double setpoint) {
		getPIDController().setSetpoint(setpoint);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
