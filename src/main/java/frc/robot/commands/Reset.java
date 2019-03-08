package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class Reset extends Command {
	// Command to test any new motors.
	public Reset() {
		requires(Robot.lifter); 
		requires(Robot.acquisition); 
	}
	final static double upliftsens = 1.5;
	final static double downliftsens = 0.5;
	final static double upacqsens = 0.5;
	final static double downacqsens = 0.2;
	final static double rollerSpeed = 0.5;
	static double acqmovement = 0;
	static double liftmovement = 0;
	boolean lifterswitch0 = false;
	boolean acquisitionswitch0 = false;
	boolean finished = false;
	@Override
	protected void initialize() {
		Robot.acquisition.getPIDController().setEnabled(false);
		Robot.lifter.getPIDController().setEnabled(false);
		Robot.acquisition.stop();
		Robot.lifter.stop();
	}
	@Override
	protected void execute() {
		liftmovement = 0;
		lifterswitch0 = Robot.lifter.get0switch();
		if(!lifterswitch0) {
			liftmovement = -1.0;
			Robot.lifter.setLift(liftmovement);
		}
		acqmovement = 0;
		acquisitionswitch0 = Robot.acquisition.getswitch0();
		if(!acquisitionswitch0) {
			acqmovement = -0.3;
			Robot.acquisition.rotate(acqmovement);
		}
		if(acqmovement + liftmovement == 0)
			finished = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.lifter.stop();
		Robot.lifter.encoderReset();
		Robot.lifter.getPIDController().setEnabled(true);
		Robot.lifter.setSetpoint(0.0);
		Robot.acquisition.encoderReset();
		Robot.acquisition.stop();
		Robot.acquisition.getPIDController().setEnabled(true);
		Robot.acquisition.setSetpoint(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.lifter.stop();
		Robot.acquisition.stop();
	}
}
