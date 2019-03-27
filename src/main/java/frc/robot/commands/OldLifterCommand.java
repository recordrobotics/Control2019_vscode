package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;
import frc.robot.OI;

public class OldLifterCommand extends Command {

  public OldLifterCommand() {
    //requires(Robot.oldLifter);
  }
  
  @Override
  protected void initialize() {
    
  }

  @Override
  protected void execute() {
        //System.out.println(OI.getOldLifter());
        //System.out.println("FIUFC");
        //Robot.oldLifter.setLift(OI.getOldLifter()+0.1);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {

  }
}
