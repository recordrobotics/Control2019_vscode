package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {
  public Autonomous() {
    // addSequential(new CenterBall(1000));
    // addSequential(new SetAcquisition(1.2));
    // addSequential(new SetRoller(0.1));
    // addSequential(new Delay(1000));
    // addSequential(new SetRoller(0.0));
    // addSequential(new SetLifter(3));
    // addSequential(new SetAcquisition(0.2));
    // addSequential(new Delay(1000));
    addSequential(new CenterTape(3000));
    // addSequential(new SetRoller(-0.1));
    // addSequential(new Delay(1000));
    // addSequential(new SetRoller(0.0));
  }
}
