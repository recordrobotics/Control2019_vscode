package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class Autonomous extends CommandGroup {
  public Autonomous() {
    AddSequential(new Reset());
    AddParallel(new SetLifter(3));
    AddParallel(new Delay(1000));
    AddSequential(new CenterTape());
    AddSequential(new SetRoller(0.2));
    AddSequential(new Delay(500));
    AddSequential(new SetRoller(0.0));
  }
}
