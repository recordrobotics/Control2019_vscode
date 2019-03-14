package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.Network;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NetworkCommand extends Command {
  private double[] input;
  private double last_l, last_r, x_i, y_i, theta_i, end_y_pos, start_angle;
  private long start_time, timeout;
  private boolean useGyro;
  private Network net;

  public NetworkCommand(Network n, double x, double y, double theta, double end, long time, boolean gyro) {
    requires(Robot.newdrivetrain);
    input = new double[3];
    last_l = 0.0;
    last_r = 0.0;
    x_i = x;
    y_i = y;
    theta_i = theta;
    end_y_pos = end;
    start_time = 0;
    start_angle = 0;
    useGyro = gyro;
    net = n;
    timeout = time;
  }

  @Override
  protected void initialize() {
    input[0] = -x_i;
    input[1] = -y_i;
    input[2] = -theta_i;
    
    Robot.newdrivetrain.stop();
    start_time = System.currentTimeMillis();
    if(useGyro) {
      start_angle = Robot.gyro.getAngle();
    }
  }

  private double[] convert_vel(double[] vel) {
    double[] output = new double[2];

    double max_vel = 0.5;
    double min_vel = 0.22;

    output[0] = vel[0] * (max_vel - min_vel) + min_vel;
    output[1] = vel[1] * (max_vel - min_vel) + min_vel;
  
    return output;
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

    if(useGyro) {
      input[2] = (Robot.gyro.getAngle() - start_angle ) * Math.PI / 180.0 - theta_i;
    } else {
      double dtheta = (dl - dr)/Robot.drivetrainWidth;
      input[2] += dtheta;
    }

    SmartDashboard.putNumber("theta", input[2]);

    input[0] += 0.5 * (dl + dr) * Math.sin(input[2]);
    input[1] += 0.5 * (dl + dr) * Math.cos(input[2]);
    
    //input[2] = theta;

    
    double[] vel = net.feed(input);
    // SmartDashboard.putNumber("move", vel[0]);
    vel = convert_vel(vel);
    
    Robot.newdrivetrain.drive(vel[0], vel[1]);
  
    SmartDashboard.putNumber("velocity0", vel[0]);
    SmartDashboard.putNumber("velocity1", vel[1]);
    System.out.println(vel[0] + " " + vel[1]);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(OI.getRotation()) > 0.2 || Math.abs(OI.getForward()) > 0.2 || input[1] > end_y_pos || (System.currentTimeMillis() - start_time > timeout);
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
