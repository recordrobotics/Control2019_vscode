/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Network;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.Math;

/**
 * An example command.  You can replace me with your own command.
 */
public class NetworkCommand extends Command {
  private double[] input;
  private double last_l, last_r, x_i, y_i, theta_i, v_f;

  public NetworkCommand(double x, double y, double v_final) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.drivetrain);
    input = new double[4];
    last_l = 0.0;
    last_r = 0.0;
    x_i = x;
    y_i = y;
    theta_i = 0;
    v_f = v_final;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    last_l = Robot.getleftdistance();
		last_r = Robot.getrightdistance();
    theta_i = Robot.gyro.getAngle();

    input[0] = -x_i;
    input[1] = -y_i;
    input[2] = theta_i;
    input[3] = v_f;
    
    Robot.drivetrain.stop();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double l = Robot.getleftdistance();
		double r = Robot.getrightdistance();
  
    SmartDashboard.putNumber("input0", input[0]);
    SmartDashboard.putNumber("input1", input[1]);
    
  
    double dl = l - last_l;
    double dr = r - last_r;
    SmartDashboard.putNumber("dl", dl);
    SmartDashboard.putNumber("dr", dr);
    last_l = l;
    last_r = r;

    double theta = Robot.gyro.getAngle() - theta_i;
    SmartDashboard.putNumber("theta", theta);

    input[0] += 0.5 * (dl + dr) * Math.sin(theta);
    input[1] += 0.5 * (dl + dr) * Math.cos(theta);
    input[2] = theta;

    double[] vel = Robot.move_net.feed(input);
    // SmartDashboard.putNumber("move", vel[0]);
    for (int i = 0; i < 2; i++) {
      vel[i] = 0.2 * (vel[i] + 1.0);
    }
    Robot.drivetrain.drive(vel[0], vel[1]);
    SmartDashboard.putNumber("velocity0", vel[0]);
    SmartDashboard.putNumber("velocity1", vel[1]);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.drivetrain.stop();
  }
}
