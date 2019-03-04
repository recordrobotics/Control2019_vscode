package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class CenterTape extends Command {
  private double timeout, start_time;
  private boolean done;

  public CenterTape(long t) {
    timeout = t;
    start_time = 0;
    done = false;
    requires(Robot.newdrivetrain);
  }

  @Override
  protected void initialize() {
    Robot.newdrivetrain.stop();
    start_time = System.getTimeMillis();
    done = false;
  }

  @Override
  protected void execute() {
    double base_speed = 0.4;                                                                                                  
    double turn_factor = -3.0;
    double turn_clamp = 0.1;
    
    double line_error1 = SmartDashboard.getNumber("tapes|PI_1", -2.0);
    //double line_error2 = SmartDashboard.getNumber("tapes|PI_2", -2.0);
    
    if(line_error1 >= -1.0) {
      Robot.newdrivetrain.curvatureDrive(-base_speed, -Math.max(-turn_clamp, Math.min(turn_clamp, line_error1 * turn_factor)));
    } else {
      done = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return done || (System.getTimeMillis() - start_time > timeout);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.newdrivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.newdrivetrain.stop();
  }
}
