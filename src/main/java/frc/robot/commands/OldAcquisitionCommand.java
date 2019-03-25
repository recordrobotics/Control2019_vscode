package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class OldAcquisitionCommand extends Command {
	public OldAcquisitionCommand(/*double rv*/) {
		//reset_vel = rv;
		requires(Robot.acquisition);
	}
	// Joystick inputs
	/*int raisebutton;
	int lowerbutton;*/
	double rollbutton;
	double slowrollbutton;
	/*boolean raisebuttonpressed;
	boolean lowerbuttonpressed;
	boolean release;
	// Switches
	boolean switch0; 
	boolean switch1;
	// Encoders
	double acquisitionpos;
	double liftpos;
	long start_t = 0;
	long middle_t = 0;

	double movement;
	int reset;
	//double rate = 0;
	//boolean lockraise;
	
	final static double updaterate = 0.04;
	// Threshold for lift encoder values below which acquisition cannot drop down to bottom state
	final static double dropthreshold = -1e9;

	private double reset_vel = 0.3;
	private double nextrange = 0.0;*/
	
	@Override
	protected void initialize() {
		/*Robot.acquisition.stop();
		Robot.acquisition.getPIDController().setEnabled(false);
		reset = 1;
		start_t = System.currentTimeMillis();
		middle_t = 0;
		nextrange = 0.0;
		SmartDashboard.putNumber("acquisitioncommand.lowerRange", nextrange);
		Robot.acquisition.getPIDController().setInputRange(nextrange, 1.2);*/
		Robot.acquisition.roll(0.0);
	}
	@Override
	protected void execute() {
		/*movement = 0;
		liftpos = Robot.lifter.getlifterpos();
		acquisitionpos = Robot.acquisition.getacquisitionpos();
		raisebutton = -1*(OI.getRaiseButton() ? 1 : 0);
		lowerbutton = OI.getLowerButton() ? 1 : 0;
		raisebuttonpressed = OI.getRaiseButtonPressed();
		lowerbuttonpressed = OI.getLowerButtonPressed();*/
		rollbutton = OI.getRollButton();
		slowrollbutton = OI.getSlowRollButton();
		/*switch0 = Robot.acquisition.getswitch0();
		switch1 = Robot.acquisition.getswitch1();
		//release = OI.getAcquisitionRelease();
		// Reset the lift back to default position, and reset encoder values if necessary
		
		if(OI.getRangeButtonPressed()) {
			nextrange -= 0.4;
			Robot.acquisition.getPIDController().setInputRange(nextrange, 1.2);
			SmartDashboard.putNumber("acquisitioncommand.lowerRange", nextrange);
		}

		if(OI.getRangeButton2Pressed()) {
			nextrange += 0.4;
			SmartDashboard.putNumber("acquisitioncommand.lowerRange", nextrange);
			Robot.acquisition.getPIDController().setInputRange(nextrange, 1.2);
		}

		if(reset == 1) {
			if(System.currentTimeMillis() - start_t < 500)
				Robot.acquisition.rotate(0.2);
			else if(middle_t > 0) {
				if(System.currentTimeMillis() - middle_t > 300 || switch0) {
					reset = 0;
					Robot.acquisition.encoderReset();
				Robot.acquisition.stop();
				Robot.acquisition.getPIDController().setEnabled(true);
				Robot.acquisition.setSetpoint(0.0);
				}
			}
			else if(!switch0) {
				if(System.currentTimeMillis() - start_t > 1500) { 
					middle_t = System.currentTimeMillis();
					Robot.acquisition.rotate(0.0);
				} 
				else {
					Robot.acquisition.rotate(-reset_vel);
				}
			}
			else {
				reset = 0;
				Robot.acquisition.encoderReset();
				Robot.acquisition.stop();
				Robot.acquisition.getPIDController().setEnabled(true);
				Robot.acquisition.setSetpoint(0.0);
			}
		}
		else if(switch0) {
			Robot.acquisition.encoderReset();
			acquisitionpos = Robot.acquisition.getacquisitionpos();
		}
		*/
		// Can roll in two directions based on which roll button is pressed
		if(slowrollbutton != 0.0)
			Robot.acquisition.roll(0.4 * slowrollbutton);
		else if(rollbutton > 0.0)
			Robot.acquisition.roll(rollbutton * 0.7);
		else
			Robot.acquisition.roll(rollbutton);
		// Raise button and lower button are not pushed at the same time, and one is active
		/*if(reset == 0 && raisebutton+lowerbutton != 0)
		{
			
				movement += raisebutton;
				movement += lowerbutton;
		}
		if (release) {
			Robot.acquisition.rotate(0.0);
			Robot.acquisition.getPIDController().setEnabled(true);
			Robot.acquisition.setSetPoint(Robot.acquisition.getacquisitionpos());
		}
		
		//SmartDashboard.putNumber("acquisition.movement", movement);
		}
		double tempupdaterate = updaterate;
		if(raisebuttonpressed || lowerbuttonpressed)
			tempupdaterate *= 3;
		SmartDashboard.putNumber("acquisitionCommand.setpoint", Robot.acquisition.getSetpoint());
		Robot.acquisition.setSetpoint(Robot.acquisition.getSetPoint() + tempupdaterate*movement);
		//Robot.acquisition.rotate(-0.5);
		//rate = Robot.acquisition.getEncoderRate();*/
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		//Robot.acquisition.setSetpoint(Robot.acquisition.getacquisitionpos());
		Robot.acquisition.roll(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		//Robot.acquisition.setSetpoint(Robot.acquisition.getacquisitionpos());
		Robot.acquisition.roll(0.0);
	}
}
