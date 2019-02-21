package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

//public class Reset extends Command {
	// Command to test any new motors.
	/*public Reset() {
		requires(Robot.lifter); 
		requires(Robot.acquisition); 
	}
	final static double upliftsens = 1.5;
	final static double downliftsens = 0.5;
	final static double upacqsens = 0.5;
	final static double downacqsens = 0.2;
	final static double rollerSpeed = 0.5;
	boolean switch0 = false;
	@Override
	protected void initialize() {
	}
	@Override
	protected void execute() {
		switch0 = Robot.lifter.get0switch();
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
	}*/
//}
