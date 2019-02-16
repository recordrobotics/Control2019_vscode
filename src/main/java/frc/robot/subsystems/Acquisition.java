package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.AcquisitionCommand;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class Acquisition extends Subsystem {
	WPI_VictorSPX acquisitionMotor = new WPI_VictorSPX(RobotMap.acquisitionPort);
	WPI_VictorSPX rollerMotor = new WPI_VictorSPX(RobotMap.rollerPort);
	// Encoder used in evaluating position of acquisition
	public static Encoder acquisition_encoder = new Encoder(RobotMap.acquisition_encoderPort1, 
		RobotMap.acquisition_encoderPort2, false, Encoder.EncodingType.k1X);
	// Magnetic switch also used to evalutae position of robot
	public static DigitalInput acqswitch = new DigitalInput(RobotMap.acquisition_magneticPort);
	// Value which will be used to convert the raw encoder output into more easily usable numbers
	final static double encoder_conv = 1;
	final static double acquisitionsens = 0.5;
	final static double motorsens = 0.5;
	
	public boolean getSwitch() {
	  return acqswitch.get();
	}

	public double getacquisitionpos () {
		return acquisition_encoder.getDistance() * encoder_conv;
	}


	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new AcquisitionCommand());
	}

	public void rotate(double x) {
		acquisitionMotor.set(ControlMode.PercentOutput, x);
	} 

	public void roll(double x) {
		rollerMotor.set(ControlMode.PercentOutput, x);
	}

	public void stop() {
		acquisitionMotor.set(ControlMode.PercentOutput, 0);
	}
}