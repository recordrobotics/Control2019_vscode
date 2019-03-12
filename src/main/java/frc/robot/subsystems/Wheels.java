package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ManualDrive;
import frc.robot.RobotMap;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

public class Wheels extends Subsystem {
	Spark frontRight = new Spark(RobotMap.driveFrontRightPortOld);
	Spark backRight = new Spark(RobotMap.driveBackRightPortOld);
	Spark frontLeft = new Spark(RobotMap.driveFrontLeftPortOld);
	Spark backLeft = new Spark(RobotMap.driveBackLeftPortOld);

	public static Encoder left_encoder = new Encoder(RobotMap.leftEncoderPort1Old, RobotMap.leftEncoderPort2Old, false, Encoder.EncodingType.k1X);
    public static Encoder right_encoder = new Encoder(RobotMap.rightEncoderPort1Old, RobotMap.rightEncoderPort2Old, false, Encoder.EncodingType.k1X);

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

	public void curvatureDrive(double forw, double rot) {
		drive(-forw + rot, -forw - rot);
	}

	public void drive(double l, double r) {
		frontLeft.set(l);
		backLeft.set(l);
		frontRight.set(-r);
		backRight.set(-r);
	}

	public void pidWrite(double l, double r) {
		frontLeft.pidWrite(l);
		backLeft.pidWrite(l);
		frontRight.pidWrite(-r);
		backRight.pidWrite(-r);
	}
	
	public void stop() {
		drive(0.0, 0.0);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ManualDrive());
	}
}
