package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SimpleAcquisitionCommand extends Command {
	public SimpleAcquisitionCommand(/*double rv*/) {
		//reset_vel = rv;
		requires(Robot.acquisition);
	}

	private final double upsens = 0.7;
	private final double downsens = 0.3;
	private final double rollsens = 0.4;
	private final double initialFactor = 0.2;

	@Override
	protected void initialize() {
		Robot.acquisition.getPIDController().setEnabled(false);
	}
	@Override
	protected void execute() {
		if(OI.right.getRawButton(5))
			Robot.acquisition.acquisitionMotor.set(ControlMode.PercentOutput, initialFactor - upsens);
		else if (OI.right.getRawButton(6))
			Robot.acquisition.acquisitionMotor.set(ControlMode.PercentOutput, initialFactor + downsens);
		else
			Robot.acquisition.acquisitionMotor.set(ControlMode.PercentOutput, initialFactor);
		
		if(OI.right.getRawButton(10))
			Robot.acquisition.rollerMotor.set(ControlMode.PercentOutput, rollsens);
		else if(OI.right.getRawButton(12))
			Robot.acquisition.rollerMotor.set(ControlMode.PercentOutput, -rollsens);
		else
			Robot.acquisition.rollerMotor.set(ControlMode.PercentOutput, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.acquisition.acquisitionMotor.set(ControlMode.PercentOutput, 0);
		Robot.acquisition.rollerMotor.set(ControlMode.PercentOutput, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.acquisition.acquisitionMotor.set(ControlMode.PercentOutput, 0);
		Robot.acquisition.rollerMotor.set(ControlMode.PercentOutput, 0);
	}
}
