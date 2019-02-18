package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.AcquisitionCommand;
import frc.robot.commands.Test;
import frc.robot.RobotMap;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class Acquisition extends Subsystem {
	WPI_VictorSPX acquisitionMotor = new WPI_VictorSPX(RobotMap.acquisitionPort);
	WPI_VictorSPX rollerMotor = new WPI_VictorSPX(RobotMap.rollerPort);
	// Encoder used in evaluating position of acquisition
	private static Encoder acquisition_encoder = new Encoder(RobotMap.acquisition_encoderPort1, 
		RobotMap.acquisition_encoderPort2, false, Encoder.EncodingType.k1X);
	// Magnetic switch also used to evalutae position of robot
	private static DigitalInput switch0 = new DigitalInput(RobotMap.acquisition0Port);
	private static DigitalInput switch1 = new DigitalInput(RobotMap.acquisition1Port);
	// Value which will be used to convert the raw encoder output into more easily usable numbers
	final static double encoder_conv = 1;
	final static double acquisitionsens = 0.5;
	final static double motorsens = 0.5;
	
	public boolean getswitch0() {
	  return switch0.get();
	}

	public boolean getswitch1() {
		return switch1.get();
	}

	public double getacquisitionpos () {
		return acquisition_encoder.getDistance() * encoder_conv;
	}

	public void encoderReset() {
		acquisition_encoder.reset();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		if(Robot.acquisition_enable)
			setDefaultCommand(new AcquisitionCommand());
		else if(Robot.test_enable)
			setDefaultCommand(new Test());
	}

	public void rotate(double x) {
		if(x < 0 && getswitch0() || x > 0 && getswitch1())
			x = 0;
		acquisitionMotor.set(ControlMode.PercentOutput, x);
	} 

	public void roll(double x) {
		rollerMotor.set(ControlMode.PercentOutput, x);
	}

	public void stop() {
		acquisitionMotor.set(ControlMode.PercentOutput, 0);
	}
}