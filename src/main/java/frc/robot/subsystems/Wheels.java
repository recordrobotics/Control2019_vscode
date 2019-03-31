package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ManualDrive;
import frc.robot.RobotMap;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

public class Wheels extends Subsystem {
	WPI_VictorSPX frontRight = new WPI_VictorSPX(RobotMap.driveFrontRightPort);
	WPI_VictorSPX backRight = new WPI_VictorSPX(RobotMap.driveBackRightPort);
	WPI_VictorSPX frontLeft = new WPI_VictorSPX(RobotMap.driveFrontLeftPort);
	WPI_VictorSPX backLeft = new WPI_VictorSPX(RobotMap.driveBackLeftPort);
	
	public Wheels() {
		backRight.follow(frontRight);
		backLeft.follow(frontLeft);
	}
	public void curvatureDrive(double forw, double rot) {
		drive(-forw + rot, -forw - rot);
	}

	public void drive(double l, double r) {
		frontLeft.set(ControlMode.PercentOutput, l);
		frontRight.set(ControlMode.PercentOutput, -r);
	}

	public void pidWrite(double l, double r) {
		frontLeft.pidWrite(l);
		frontRight.pidWrite(-r);
	}
	
	public void stop() {
		frontRight.set(ControlMode.PercentOutput, 0);
		frontLeft.set(ControlMode.PercentOutput, 0);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ManualDrive());
	}
}
