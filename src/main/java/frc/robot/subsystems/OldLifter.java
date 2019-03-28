package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Spark;
//import frc.robot.commands.Test;
import frc.robot.commands.OldLifterCommand;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.Robot;


public class OldLifter extends Subsystem { // This system extends PIDSubsystem
	Spark liftMotor = new Spark(RobotMap.oldLiftPort);
	final static double raiseSpeed = 0.5;
	final static double lowerSpeed = 0.3;
	
    public void initDefaultCommand() {
		setDefaultCommand(new OldLifterCommand());
	}
	
	public void setLift(double x) {
		if(x > 0)
			x*= raiseSpeed;
		if(x < 0)
            x*= lowerSpeed;
        
		liftMotor.set(x);
    }
    
	public void stop() {
		liftMotor.set(0);
    }
    

}
