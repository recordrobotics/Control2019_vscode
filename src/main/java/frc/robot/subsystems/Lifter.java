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
	// Proportional, Integral and Derivative R values for PID Loop
	private final static double Rp = 20.0;
	private final static double Ri = 0.0;
	private final static double Rd = 10.0;
	private final static double tolerance = 0.01;
	private final static double encoder_conv = -1.0/7000.0;
	private final double[] auto_positions = {0.0, 0.10471428, 0.971, 1.171, 1.9462857, 2.0957};
	final static double raiseSpeed = 0.5;
	final static double lowerSpeed = 0.3;
	
    public final double[] auto_positions = {0.0, 0.10471428, 0.971, 1.171, 1.9462857, 2.0957};

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
		if(Robot.lifter_enable)
			setDefaultCommand(new LifterCommand());
		else if(Robot.test_enable)
			setDefaultCommand(new Test());
	}
	
	public void setLift(double x) {
		if(x > 0)
			x*= raiseSpeed;
		if(x < 0)
			x*= lowerSpeed;
		liftMotor.set(ControlMode.PercentOutput, x);
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
