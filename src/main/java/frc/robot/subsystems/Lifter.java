package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;


public class Lifter extends PIDSubsystem { // This system extends PIDSubsystem

	WPI_VictorSPX liftMotor = new WPI_VictorSPX(RobotMap.liftMotorPort);
	public static Encoder liftEncoder = new Encoder(RobotMap.lifter_encoderPort1, 
		RobotMap.lifter_encoderPort2, false, Encoder.EncodingType.k1X);
	public static DigitalInput liftswitch = new DigitalInput(RobotMap.lifter_magneticPort);
	private final static double liftsens = 0.5;
	private final static double Rp = 0.01;
	private final static double Ri = 0;
	private final static double Rd = 0;
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
	}
	
	public void setLift(double x) {
		liftMotor.set(ControlMode.PercentOutput, liftsens*x);
	}

	public boolean getSwitch() {
		return liftswitch.get();
	  }

	  public double getlifterpos () {
		return liftEncoder.getDistance() * encoder_conv;
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
