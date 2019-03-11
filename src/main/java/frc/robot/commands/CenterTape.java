package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Robot;

import java.nio.ByteBuffer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.*;


public class CenterTape extends Command {
  private double timeout, start_time;
  private boolean done;
  private SerialPort device;
  private SerialPort.Port kOnboard;



  public CenterTape(long t) {
    timeout = t;
    start_time = 0;
    done = false;
    requires(Robot.newdrivetrain);
    //device = new SerialPort(9600, Port.kUSB);
  }

  public long bytesToLong(byte[] b) {
    return ( ( (long) b[7]) & 0xFF) +
      ( ( ( (long) b[6]) & 0xFF) << 8) +
      ( ( ( (long) b[5]) & 0xFF) << 16) +
      ( ( ( (long) b[4]) & 0xFF) << 24) +
      ( ( ( (long) b[3]) & 0xFF) << 32) +
      ( ( ( (long) b[2]) & 0xFF) << 40) +
      ( ( ( (long) b[1]) & 0xFF) << 48) +
      ( ( ( (long) b[0]) & 0xFF) << 56);
    // ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    // buffer.put(bytes);
    // buffer.flip();//need flip 
    // return buffer.getLong();
  }

  @Override
  protected void initialize() {
    Robot.newdrivetrain.stop();
    start_time = System.currentTimeMillis();
    done = false;
  }

  @Override
  protected void execute() {
    double base_speed = 0.4;                                                                                                  
    double turn_factor = -3.0;
    double turn_clamp = 0.1;

    /*int length = device.getBytesReceived();
    byte[] data = device.read(length);
    byte[] leftByte = new byte[4];
    byte[] rightByte = new byte[4];
    for (int i = 0; i < 4; i++){
      leftByte[i] = data[i];
      rightByte[i] = data[i+4];
    }

    System.out.println("cmLeft: " + leftByte);
    System.out.println("cmRight: " + rightByte);

    // long cmLeft = bytesToLong(leftByte);
    // long cmRight = bytesToLong(rightByte);

    // System.out.println("cmLeft: " + cmLeft);
    // System.out.println("cmRight: " + cmRight);
*/
    
    double line_error1 = SmartDashboard.getNumber("tapes|PI_1", -2.0);
    //double line_error2 = SmartDashboard.getNumber("tapes|PI_2", -2.0);
    SmartDashboard.putNumber("Yay!", 1.0);
    if(line_error1 >= -1.0) {
       Robot.newdrivetrain.curvatureDrive(-base_speed, -Math.max(-turn_clamp, Math.min(turn_clamp, line_error1 * turn_factor)));
    } else {
       done = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return done || (System.currentTimeMillis() - start_time > timeout);
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
