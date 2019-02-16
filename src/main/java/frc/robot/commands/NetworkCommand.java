package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NetworkCommand extends Command {
  private double[] input;
  private double last_l, last_r, x_i, y_i, theta_i;
  private boolean trigger;
  public NetworkCommand(double x, double y, double theta) {
    requires(Robot.newdrivetrain);
    input = new double[3];
    last_l = 0.0;
    last_r = 0.0;
    x_i = x;
    y_i = y;
    theta_i = theta;
  }

  @Override
  protected void initialize() {
    last_l = Robot.newdrivetrain.getleftdistance();
		last_r = Robot.newdrivetrain.getrightdistance();
    theta_i = 0;

    input[0] = -x_i;
    input[1] = -y_i;
    input[2] = -theta_i;
    
    Robot.newdrivetrain.stop();
    trigger = false;
  }

  @Override
  protected void execute() {
    double l = Robot.newdrivetrain.getleftdistance();
		double r = Robot.newdrivetrain.getrightdistance();
  
    SmartDashboard.putNumber("input0", input[0]);
    SmartDashboard.putNumber("input1", input[1]);
    
  
    double dl = l - last_l;
    double dr = r - last_r;
    SmartDashboard.putNumber("dl", dl);
    SmartDashboard.putNumber("dr", dr);
    last_l = l;
    last_r = r;

    double dtheta = (dl - dr)/0.585;
    input[2] += dtheta;
    
    SmartDashboard.putNumber("theta", input[2]);

    input[0] += 0.5 * (dl + dr) * Math.sin(input[2]);
    input[1] += 0.5 * (dl + dr) * Math.cos(input[2]);
    if (input[1] > 0.1) 
      trigger = true;
    //input[2] = theta;

    double[] vel = Robot.move_net.feed(input);
    // SmartDashboard.putNumber("move", vel[0]);
    for (int i = 0; i < 2; i++) {
      vel[i] = 0.3 * 0.5 * (vel[i] + 1.0) + 0.25;
    }
    Robot.newdrivetrain.drive(vel[0], vel[1]);
    SmartDashboard.putNumber("velocity0", vel[0]);
    SmartDashboard.putNumber("velocity1", vel[1]);
    System.out.println(vel[0] + " " + vel[1]);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return trigger;
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
