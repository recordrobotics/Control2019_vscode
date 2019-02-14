/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.commands.ClimberCommand;
import frc.robot.RobotMap;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */

public class Climber extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private DigitalInput bottom_switch = new DigitalInput(RobotMap.bottomswitchPort);
	private DigitalInput top_switch = new DigitalInput(RobotMap.topswitchPort);
	private Spark motor = new Spark(RobotMap.climbmotorPort);
	private Encoder encoder;

	private int P, I, D = 1;
	private double integral, previous_error, setpoint = 0;
	private double rcw = 0;

	public Climber(Encoder encoder, double targetSpeed){
		this.encoder = encoder;
		this.setpoint = targetSpeed;
	}

	public void PID(){
        double error = this.setpoint - encoder.getRate(); // Error = Target - Actual
        this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        double derivative = (error - this.previous_error) / .02;
        this.rcw = P*error + I*this.integral + D*derivative;
    }

	public void setMotorToPID(){
		PID();
		setMotor(this.rcw);
	}





	public void setMotor(double x) {
		motor.set(x);
	}

	public void stopMotor() {
		motor.set(0);
	}

	public boolean getBottomSwitch() {
		return bottom_switch.get();
	}

	public boolean getTopSwitch() {
		return top_switch.get();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ClimberCommand());
	}
	
}