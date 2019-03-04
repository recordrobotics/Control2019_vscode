package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

public class Autonomous extends CommandGroup {
  public Autonomous() {
    addSequential(new Reset());
    addParallel(new SetLifter(3));
    addParallel(new Delay(1000));
    addSequential(new CenterTape(3000));
  }
}
