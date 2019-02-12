/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Counter;
import frc.robot.RobotMap;
import frc.robot.commands.LiftingCommand;



/**
 * An not-example subsystem.  You can replace me with your own Subsystem.
 * but it is!
 */
public class Lifting extends Subsystem {
  private DigitalInput inner_switch = new DigitalInput(RobotMap.innerswitchPort);
  private DigitalInput outer_switch = new DigitalInput(RobotMap.outerswitchPort);
  // private Encoder encoder = new Encoder(TEMP, TEMP, TEMP, Encoder.EncodingType.k4x);
  private Spark motor = new Spark(RobotMap.liftmotorPort);

  private Counter inner_counter = new Counter(inner_switch);
  private Counter outer_counter = new Counter(outer_switch);

	public void setMotor(double x) {
		motor.set(x);
	}

	public void stopMotor() {
		motor.set(0);
  }
  
  // Add getter method for encoder

	public boolean getInnerSwitch() {
		return inner_switch.get();
	}

	public boolean getOuterSwitch() {
		return outer_switch.get();
  }
  
  public void initializeCounter(){
    inner_counter.reset();
    outer_counter.reset();
  }

  // public void initalizeEncoder(){
  //   encoder.reset();
  // }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new LiftingCommand());

  }



}
