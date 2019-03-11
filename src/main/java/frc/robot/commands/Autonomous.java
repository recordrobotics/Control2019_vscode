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
    addSequential(new SetRoller(0.5));
    addSequential(new Delay(300));
    addSequential(new SetRoller(0.0));
    if(start == Start.LEVEL2) {
      addSequential(new SetDrive(-0.2, -0.2));
      addSequential(new Delay(2000));
      addSequential(new SetDrive(0.0, 0.0));
      addSequential(new Spin(180.0, false, 1000, Spin.Finish.PASS, 0.0));
    }
    addParallel(new LifterCommand());
    addParallel(new ManualDrive());
    addSequential(new AcquisitionCommand(0.6));
    /*addSequential(new Reset());
    addSequential(new SetAcquisition(1.5));
    addSequential(new Delay(1000));
    addSequential(new CenterBall(10000));
    addSequential(new SetAcquisition(0.8));
    addSequential(new SetRoller(0.1));
    addSequential(new Delay(400));
    addSequential(new SetRoller(0.0));
    addSequential(new SetLifter(3));
    addSequential(new SetAcquisition(0.2));
    addSequential(new Delay(2000)); 
    addSequential(new CenterTape(3000));
    addSequential(new SetRoller(-0.1));
    addSequential(new Delay(1000));
    addSequential(new Se(int(ballAvgX), int(ballAvgY))tRoller(0.0));
    addSequential(new CenterTape(3000));*/
  }
}
