package frc.robot.commands;

//import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
//import edu.wpi.first.wpilibj.smartdashboard.*;
//import frc.robot.Robot;

public class Autonomous extends CommandGroup {
  public Autonomous() {
    addSequential(new Reset());
    addSequential(new SetAcquisition(1.5));
    addSequential(new Delay(1000));
    addSequential(new CenterBall(10000));
    addSequential(new CenterTape(3000));
  }
}
