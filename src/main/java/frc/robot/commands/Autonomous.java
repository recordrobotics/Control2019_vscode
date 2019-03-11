package frc.robot.commands;

//import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
//import edu.wpi.first.wpilibj.smartdashboard.*;
//import frc.robot.Robot;

public class Autonomous extends CommandGroup {
  public enum Start {
    LEVEL2, LEVEL1
  }
  public Autonomous(Start start) {
    addParallel(new LifterCommand(true));
    if(start == Start.LEVEL2) {
      addParallel(new SetRoller(0.5, 300));
      addSequential(new SetDrive(-0.2, -0.2, 2000));
      addSequential(new Spin(180.0, false, 1000, Spin.Finish.PASS, 0.0));
    } else {
      addSequential(new SetRoller(0.5, 300));
    }
    addParallel(new ManualDrive());
    addSequential(new AcquisitionCommand());
  }
}
