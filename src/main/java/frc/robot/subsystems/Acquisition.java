package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.commands.AcquisitionCommand;
//import frc.robot.commands.Test;
import frc.robot.RobotMap;
import frc.robot.Robot;
import frc.robot.OI;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Acquisition extends PIDSubsystem {
	public WPI_VictorSPX acquisitionMotor = new WPI_VictorSPX(RobotMap.acquisitionPort);
	public WPI_VictorSPX rollerMotor = new WPI_VictorSPX(RobotMap.rollerPort);
	// Encoder used in evaluating position of acquisition
	private static Encoder acquisition_encoder = new Encoder(RobotMap.acquisition_encoderPort1, 
		RobotMap.acquisition_encoderPort2, false, Encoder.EncodingType.k1X);
	private static Encoder roller_encoder = new Encoder(RobotMap.roller_encoderPort1, 
		RobotMap.roller_encoderPort2, false, Encoder.EncodingType.k1X);
	// Magnetic switch also used to evalutae position of robot
	private static DigitalInput switch0 = new DigitalInput(RobotMap.acquisition0Port);
	private static DigitalInput switch1 = new DigitalInput(RobotMap.acquisition1Port);
	// Value which will be used to convert the raw encoder output into more easily usable numbers
	final static double encoder_conv = -1.0/19000.0;
	final static double encoder_cons = 2.2105263158;
	private final static double Rp = 1.0;
	private final static double Ri = 0.0;
	private final static double Rd = 0.2;
	private final static double tolerance = 0.01;
	final static double acquisitionRaiseSpeed = 1.0;
	final static double acquisitionLowerSpeed = 0.6;

	private final static double upperRange = 1.2;
	private final static double lowerRange = 0.0;

	private double rangeOffset = 0.0;

	// Positions for ball depositing
	public final static double bottom_hatch = -1.0;
	public final static double bottom_ball = 1.0;
	public final static double middle_hatch = -1.0;
	public final static double middle_ball = 1.1;
	public final static double top_hatch = -1.0;
	public final static double top_ball = 1.5;

	public final double[] auto_positions = {bottom_hatch, bottom_ball, middle_hatch, 
		middle_ball, top_hatch, top_ball};

	public void setDepositPos(int i) {
		if (Robot.goingForBalls() && i > -1 && i < auto_positions.length && auto_positions[i] >= 0.0) {
			setSetpoint(auto_positions[i]);
		}
	}

	public Acquisition() {
		super("Acquisition", Rp, Ri, Rd);// The constructor passes a name for the subsystem and the P, I and D constants that are used when computing the motor output
		setAbsoluteTolerance(tolerance);
		getPIDController().setContinuous(false);
		setSetPoint(0);
		getPIDController().setEnabled(false);
		getPIDController().setInputRange(lowerRange, upperRange);
		getPIDController().setOutputRange(-1.0, 1.0);
	}

	public void incrementOffset(double v) {
		rangeOffset += v;
		SmartDashboard.putNumber("acquisition.rangeOffset", rangeOffset);
	}
	
	public boolean getswitch0() {
	  return !switch0.get();
	}

	public boolean getswitch1() {
		return !switch1.get();
	}

	public double getacquisitionpos () {
		return acquisition_encoder.getDistance() * encoder_conv + rangeOffset;
	}

	public double getrollerpos () {
		return roller_encoder.getDistance() * encoder_conv + encoder_cons;
	}

	public void encoderReset() {
		acquisition_encoder.reset();
	}

	public double getEncoderRate() {
		return acquisition_encoder.getRate();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		//if(Robot.acquisition_enable)
			setDefaultCommand(new AcquisitionCommand());
		//else if(Robot.test_enable)
		//	setDefaultCommand(new Test());
	}

	public void rotate(double x) {
		acquisitionMotor.set(ControlMode.PercentOutput, -x);
	} 

	public void roll(double x) {
		SmartDashboard.putNumber("acquisition.roll", x);
		rollerMotor.set(ControlMode.PercentOutput, x);
	}

	public void stop() {
		acquisitionMotor.set(ControlMode.PercentOutput, 0);
		rollerMotor.set(ControlMode.PercentOutput, 0);
	}

	protected double returnPIDInput() {
    	return getacquisitionpos();
	}

	public void setSetPoint(double setpoint) {
		getPIDController().setSetpoint(setpoint);
	}

	public double getSetPoint() {
		return getPIDController().getSetpoint();
	}

	protected void usePIDOutput(double output) {
		if(output < 0 && getswitch0() || output > 0 && getswitch1())
			output = 0;
		else if(output < 0)
			output = output * acquisitionRaiseSpeed;
		else if(output > 0)
			output *= acquisitionLowerSpeed;
		acquisitionMotor.pidWrite(-output); // this is where the computed output value fromthe PIDController is applied to the motor
	}
}
