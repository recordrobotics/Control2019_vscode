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
	public static Encoder liftEncoder;// = new Encoder(RobotMap.lifter_encoderPort1, 
//s		RobotMap.lifter_encoderPort2, false, Encoder.EncodingType.k1X);
	public static DigitalInput switch0_1;// = new DigitalInput(RobotMap.lifter1Port);
	public static DigitalInput switch1_2;// = new DigitalInput(RobotMap.lifter2Port);
	public static DigitalInput resetswitch;// = new DigitalInput(RobotMap.lifter0Port);
	// Proportional, Integral and Derivative R values for PID Loop
	private final static double Rp = 20.0;
	private final static double Ri = 0.0;
	private final static double Rd = 10.0;
	private final static double tolerance = 0.01;
	private final static double encoder_conv = -1.0/7000.0;
	final static double raiseSpeed = 0.5;
	final static double lowerSpeed = 0.3;
	public final static double bottom_hatch = 0.0;
	public final static double bottom_ball = 0.10471428;
	public final static double middle_hatch = 0.971;
	public final static double middle_ball = 1.171;
	public final static double top_hatch = 1.9462857;
	public final static double top_ball = 2.0957;

	public final double[] auto_positions = {bottom_hatch, bottom_ball, middle_hatch, 
		middle_ball, top_hatch, top_ball};

	public Lifter() {
		super("Lifter", Rp, Ri, Rd);// The constructor passes a name for the subsystem and the P, I and D constants that are used when computing the motor output
		setAbsoluteTolerance(tolerance);
		getPIDController().setContinuous(false);
		setSetPoint(0);
		getPIDController().setEnabled(false);
		getPIDController().setInputRange(-0.1, 2.15);
		getPIDController().setOutputRange(-1.0, 1.0);
	}
	
  public void initDefaultCommand() {
	/*if(Robot.lifter_enable)
			setDefaultCommand(new LifterCommand(false));
		else if(Robot.test_enable)
			setDefaultCommand(new Test());*/
	}
	
	public void setLift(double x) {
		if(x > 0)
			x*= raiseSpeed;
		if(x < 0)
			x*= lowerSpeed;
		liftMotor.set(ControlMode.PercentOutput, x);
	}
	/*
	public void incAuto() {
		int auto_position_index = auto_positions.length - 1;
		for(int i = auto_positions.length - 1; i >= 0; i--) {
			if(auto_positions[i] > getlifterpos())
					auto_position_index = i;
		}
		setSetPoint(auto_positions[auto_position_index]);
	}

	public void decAuto() {
		int auto_position_index = 0;					
		for(int i = 0; i < auto_positions.length; i++) {
			if(auto_positions[i] < getlifterpos())
				auto_position_index = i;
		}
		setSetPoint(auto_positions[auto_position_index]);
	}
	*/
	public void setAuto(double pos) {
		setSetPoint(pos);
	}
	
	public boolean get0switch() {
		return !resetswitch.get();
	}
	public boolean get1switch() {
		return !switch0_1.get();
	}
	public boolean get2switch() {
		return !switch1_2.get();
	}
	public double getlifterpos () {
		return liftEncoder.getDistance() * encoder_conv;
	}
	public double[] getAutoPositions() {
		return auto_positions;
	}
	public void encoderReset() {
		liftEncoder.reset();
	}
	public void stop() {
		liftMotor.set(ControlMode.PercentOutput, 0);
	}
  protected double returnPIDInput() {
    	return getlifterpos(); // returns the sensor value that is providing the feedback for the system
	}
	
	public void setSetPoint(double setpoint) {
			getPIDController().setSetpoint(setpoint);
	}

	public double getSetPoint() {
		return getPIDController().getSetpoint();
	}

  protected void usePIDOutput(double output) {
		if(output < 0 && get0switch() || output > 0 && get1switch() && get2switch())
			output = 0;
		else if(output > 0)
			output *= raiseSpeed;
		else if(output < 0)
			output *= lowerSpeed;

		//System.out.println(output);
   	liftMotor.pidWrite(output); // this is where the computed output value fromthe PIDController is applied to the motor
  }
}
