package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {
  public Autonomous() {
    addParallel(new SetLifter(3));
    addParallel(new Delay(1000));
    addSequential(new CenterTape());
    addSequential(new SetRoller(0.2));
    addSequential(new Delay(500));
    addSequential(new SetRoller(0.0));
  }
}
