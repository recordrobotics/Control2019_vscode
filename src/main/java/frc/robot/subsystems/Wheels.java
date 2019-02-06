/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ManualDrive;
import frc.robot.RobotMap;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */

public class Wheels extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	Spark frontRight = new Spark(RobotMap.driveFrontRightPort);
	Spark backRight = new Spark(RobotMap.driveBackRightPort);
	SpeedControllerGroup right = new SpeedControllerGroup(frontRight, backRight);

	Spark frontLeft = new Spark(RobotMap.driveFrontLeftPort);
	Spark backLeft = new Spark(RobotMap.driveBackLeftPort);
	SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, backLeft);

	private final double turnsens = 1;
	private final double maxsens = 0.8;
	private final double forwdec = 0.05;
	private final double backdec = 0.1;
	private final double inc = 0.05;
	private final double maxAccel = 0.002;
	private double sens = maxsens;

	public void curvatureDrive(double forw, double rot, double accel) {
		if(accel > maxAccel)
			sens -= forwdec;
		else if(accel < -maxAccel)
			sens -= backdec;
		else if(sens < maxsens)
			sens += inc;
		System.out.println(sens);
		right.set(sens * (forw + turnsens * rot));
		left.set(-sens * (forw - turnsens * rot));
	}

	public void drive(double l, double r) {
		left.set(l);
		right.set(-r);
	}
	
	public void stop() {
		left.set(0);
		right.set(0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ManualDrive());
	}
}