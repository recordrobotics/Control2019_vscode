/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.AcquisitionCommand;
import frc.robot.commands.ManualDrive;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;


/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */

public class Acquisition extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	WPI_VictorSPX acquisitionMotor = new WPI_VictorSPX(RobotMap.acquisitionPort);
	WPI_VictorSPX rollerMotor = new WPI_VictorSPX(RobotMap.rollerPort);
	public static Encoder acquisition_encoder = new Encoder(RobotMap.acquisition_encoderPort1, 
		RobotMap.acquisition_encoderPort2, false, Encoder.EncodingType.k1X);
	public static DigitalInput lswitch = new DigitalInput(RobotMap.aquisition_magneticPort);
	final static double encoder_conv = 1;
	final static double acquisitionsens = 0.5;
	final static double motorsens = 0.5;
	public boolean getSwitch() {
	  return lswitch.get();
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