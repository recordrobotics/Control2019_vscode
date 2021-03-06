package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ManualDrive;
import frc.robot.RobotMap;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.Encoder;

public class NewWheels extends Subsystem {
	WPI_VictorSPX frontRight = new WPI_VictorSPX(RobotMap.driveFrontRightPort);
	WPI_VictorSPX backRight = new WPI_VictorSPX(RobotMap.driveBackRightPort);
	WPI_VictorSPX frontLeft = new WPI_VictorSPX(RobotMap.driveFrontLeftPort);
	WPI_VictorSPX backLeft = new WPI_VictorSPX(RobotMap.driveBackLeftPort);

	private long disabled_time = 0;
	private long disabled_start_time = 0;

	public static Encoder left_encoder = new Encoder(RobotMap.leftEncoderPort1, RobotMap.leftEncoderPort2, false, Encoder.EncodingType.k1X);
    public static Encoder right_encoder = new Encoder(RobotMap.rightEncoderPort1, RobotMap.rightEncoderPort2, false, Encoder.EncodingType.k1X);

	private double turnsens = 0.8;
	private double maxsens = 0.5;
	
	/*
	private final double dec = 0.1;
	private final double maxAccel = 0.0015;
	*/
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

	public boolean disabled() {
		return System.currentTimeMillis() - disabled_start_time < disabled_time; 
	}

	public void disable(long t) {
		disabled_time = t;
		disabled_start_time = System.currentTimeMillis();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//if(Robot.drive_enable)
			setDefaultCommand(new ManualDrive());
	}
}
