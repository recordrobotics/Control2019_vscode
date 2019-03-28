package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SimpleLifterCommand extends Command {
	public SimpleLifterCommand() {
		requires(Robot.lifter);
	}

	@Override
	protected void initialize() {
		Robot.lifter.getPIDController().setEnabled(false);
		Robot.lifter.setLift(0);
	}
	@Override
	protected void execute() {
		if(OI.right.getRawButton(3))
			Robot.lifter.setLift(1.0);
		else if (OI.right.getRawButton(4))
			Robot.lifter.setLift(-1.0);
		else
			Robot.lifter.setLift(0);
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
