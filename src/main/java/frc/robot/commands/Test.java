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
	final static double upliftsens = 1.5;
	final static double downliftsens = 0.5;
	final static double upacqsens = 0.5;
	final static double downacqsens = 0.2;
	final static double rollerSpeed = 0.5;
	@Override
	protected void initialize() {
	}
	@Override
	protected void execute() {
		/*
		int lift = (OI.getManualRaiseButton() ? 1 : 0) - (OI.getManualLowerButton() ? 1 : 0);
		double liftsens = 0.0;
		if(lift > 0 && !Robot.lifter.get2switch()) {
			liftsens = upliftsens;
		}
		else if(!Robot.lifter.get0switch())
			liftsens = downliftsens;
		else {
			//SmartDashboard.putNumber("LifterBottomSwitch", Robot.lifter.get0switch() ? 1 : 0);
			//SmartDashboard.putNumber("LifterTopSwitch", Robot.lifter.get2switch() ? 1 : 0);
			//System.out.println((Robot.lifter.get0switch() ? 1 : 0) + " " + (Robot.lifter.get2switch() ? 1 : 0));
		}
		Robot.lifter.setLift(liftsens * lift);
		//SmartDashboard.putNumber("LiftEncoder", Robot.lifter.getlifterpos());
		//System.out.println("LiftEncoder: " + Robot.lifter.getlifterpos());
		*/
		double acquisitionsens = 0.0;
		int roll = (OI.getRaiseButton() ? 1 : 0) - (OI.getLowerButton() ? 1 : 0);
		int rollbutton = OI.getRollButton();
		
		if(roll > 0 && (!Robot.acquisition.getswitch1())) {
			acquisitionsens = upacqsens;
			//System.out.println(acquisitionsens);
		}
		else if(!Robot.acquisition.getswitch0())
			acquisitionsens = downacqsens;
		else {
			SmartDashboard.putNumber("AcquisitionEncoder", Robot.acquisition.getacquisitionpos());
		}
		Robot.acquisition.roll(rollerSpeed*rollbutton);
		Robot.acquisition.rotate(acquisitionsens * roll);
		//SmartDashboard.putNumber("AcquisitionEncoder", Robot.acquisition.getacquisitionpos());
		//System.out.println("AcquisitionEncoder: " + Robot.acquisition.getacquisitionpos());
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
