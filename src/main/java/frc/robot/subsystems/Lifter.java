package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.commands.Test;
import frc.robot.commands.LifterCommand;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;
import frc.robot.Robot;


public class Lifter extends PIDSubsystem { // This system extends PIDSubsystem

	WPI_VictorSPX liftMotor = new WPI_VictorSPX(RobotMap.liftPort);
	public static Encoder liftEncoder = new Encoder(RobotMap.lifter_encoderPort1, 
		RobotMap.lifter_encoderPort2, false, Encoder.EncodingType.k1X);
	public static DigitalInput switch0_1 = new DigitalInput(RobotMap.lifter1Port);
	public static DigitalInput switch1_2 = new DigitalInput(RobotMap.lifter2Port);
	public static DigitalInput resetswitch = new DigitalInput(RobotMap.lifter0Port);
	private final static double liftsens = 0.5;
	// Proportional, Integral and Derivative R values for PID Loop
	private final static double Rp = 0.01;
	private final static double Ri = 0;
	private final static double Rd = 0;
	// Tolerance?
	private final static double tolerance = 0.01;
	private final static double encoder_conv = 0.001;
	private static int state;

	public Lifter() {
		super("Lifter", Rp, Ri, Rd);// The constructor passes a name for the subsystem and the P, I and D constants that are used when computing the motor output
		setAbsoluteTolerance(tolerance);
		getPIDController().setContinuous(false);
		setSetPoint(0);
		getPIDController().setEnabled(false);
		state = 0;
	}
	
  public void initDefaultCommand() {
		if(Robot.lifter_enable)
			setDefaultCommand(new LifterCommand());
		else if(Robot.test_enable)
		setDefaultCommand(new Test());
	}
	
	public void setLift(double x) {
		System.out.println(liftsens*x);
		liftMotor.set(ControlMode.PercentOutput, liftsens*x);
	}

	public boolean get0switch() {
		return resetswitch.get();
		}
	public boolean get1switch() {
			return switch0_1.get();
	}
	public boolean get2switch() {
		return switch1_2.get();
}
	  public double getlifterpos () {
		return liftEncoder.getDistance() * encoder_conv;
	}

	public void encoderReset() {
		liftEncoder.reset();
	}
	public void stop() {
		liftMotor.set(ControlMode.PercentOutput, 0);
	}
    protected double returnPIDInput() {
    	return liftEncoder.getDistance() * encoder_conv; // returns the sensor value that is providing the feedback for the system
	}
	
	public void setSetPoint(double setpoint) {
		if(!(state == 2 && state < setpoint*encoder_conv || state == 0 && state > setpoint*encoder_conv))
			getPIDController().setSetpoint(setpoint);
	}

    protected void usePIDOutput(double output) {
    	liftMotor.pidWrite(output); // this is where the computed output value fromthe PIDController is applied to the motor
    }
}
