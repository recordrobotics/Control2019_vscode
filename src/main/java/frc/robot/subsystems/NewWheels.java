/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ManualDrive;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.Encoder;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */

public class NewWheels extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	WPI_VictorSPX frontRight = new WPI_VictorSPX(RobotMap.driveFrontRightPort);
	WPI_VictorSPX backRight = new WPI_VictorSPX(RobotMap.driveBackRightPort);
	
	WPI_VictorSPX frontLeft = new WPI_VictorSPX(RobotMap.driveFrontLeftPort);
	WPI_VictorSPX backLeft = new WPI_VictorSPX(RobotMap.driveBackLeftPort);

	public static Encoder left_encoder = new Encoder(RobotMap.leftEncoderPort1, RobotMap.leftEncoderPort2, false, Encoder.EncodingType.k1X);
    public static Encoder right_encoder = new Encoder(RobotMap.rightEncoderPort1, RobotMap.rightEncoderPort2, false, Encoder.EncodingType.k1X);

	private double turnsens = 0.8;
	private double maxsens = 0.5;
	private final double dec = 0.1;
	private final double maxAccel = 0.0015;
	private double sens = maxsens;
	final static double encoder_conv = 1.0/745.0;

	public double getleftdistance() {
		return -left_encoder.getDistance() * encoder_conv;
	}
	
  	public double getrightdistance() {
  		return right_encoder.getDistance() * encoder_conv;
	}

	public NewWheels() {
		backRight.follow(frontRight);
		backLeft.follow(frontLeft);
	}
	public void curvatureDrive(double forw, double rot) {
		if (forw < 0.3)
			turnsens = 0.5;
		else
			turnsens = 0.3;
		frontRight.set(ControlMode.PercentOutput, sens * forw + turnsens * rot);
		frontLeft.set(ControlMode.PercentOutput, -(sens * forw - turnsens * rot));
	}

	public void drive(double l, double r) {
		frontRight.set(ControlMode.PercentOutput, l);
		frontLeft.set(ControlMode.PercentOutput, -r);
	}
	
	public void stop() {
		frontRight.set(ControlMode.PercentOutput, 0);
		frontLeft.set(ControlMode.PercentOutput, 0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new ManualDrive());
	}
}