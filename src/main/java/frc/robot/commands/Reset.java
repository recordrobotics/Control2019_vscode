package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class Reset extends Command {
	// Command to test any new motors.
	//boolean moveforw;
	public Reset(/*boolean move, */long t) {
		//moveforw = move;
		timeout = t;
		requires(Robot.lifter); 
		//requires(Robot.acquisition); 
	}
	final static double upliftsens = 1.5;
	final static double downliftsens = 0.5;
	//final static double upacqsens = 0.5;
	//final static double downacqsens = 0.2;
	//static double acqmovement = 0;
	static double liftmovement = 0;
	boolean lifterswitch0 = false;
	//boolean acquisitionswitch0 = false;
	boolean finished = false;
	long start_t = 0;
	long timeout = 0;
	//long middle_t = 0;
	@Override
	protected void initialize() {
		//Robot.acquisition.getPIDController().setEnabled(false);
		Robot.lifter.getPIDController().setEnabled(false);
		//Robot.acquisition.stop();
		Robot.lifter.stop();
		start_t = System.currentTimeMillis();
	}
	@Override
	protected void execute() {
		/*if(moveforw) {
			if(System.currentTimeMillis() - start_t < 500)
				Robot.acquisition.rotate(0.2);
			else
				moveforw = false;
		}
		else {
			if(middle_t == 0) {*/
				liftmovement = 0;
				lifterswitch0 = Robot.lifter.get0switch();
				if(!lifterswitch0) {
					liftmovement = -1.0;
					Robot.lifter.setLift(liftmovement);
				}
				/*acqmovement = 0;
				acquisitionswitch0 = Robot.acquisition.getswitch0();
				if(!acquisitionswitch0) {
					acqmovement = -0.3;
					Robot.acquisition.rotate(acqmovement);
				}*/
				if(/*acqmovement + */liftmovement == 0 || (System.currentTimeMillis() - start_t > timeout))
					finished = true;
				/*else if(System.currentTimeMillis() - start_t > 1500) { 
					middle_t = System.currentTimeMillis();
					Robot.acquisition.rotate(0.0);
				}
			}
			else if(System.currentTimeMillis() - middle_t > 300 || Robot.acquisition.getswitch0()) {
				finished = true;
			}
		}*/
	}

	// Make this return true when this Comma-nd no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.lifter.encoderReset();
		Robot.lifter.stop();
		Robot.lifter.getPIDController().setEnabled(true);
		Robot.lifter.setSetpoint(0.0);

		/*Robot.acquisition.encoderReset();
		Robot.acquisition.stop();
		Robot.acquisition.getPIDController().setEnabled(true);
		Robot.acquisition.setSetpoint(0.0);*/
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.lifter.stop();
		//Robot.acquisition.stop();
	}
}
