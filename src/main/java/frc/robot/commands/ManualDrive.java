package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Network;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.net.Socket;
import java.lang.Thread;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;

public class ManualDrive extends Command {
	public ManualDrive() {
		//requires(Robot.drivetrain);
		requires(Robot.newdrivetrain);

	}

	Thread auto_thread = null;

	long[] f_start_time = {0};
	long[] r_start_time = {0};

	final long f_warmup = 500;
	final long r_warmup = 500;
	final double f_sens = 0.5;
	final double r_sens = 0.25;
	final double f_pow = 1.0;
	final double r_pow = 1.0;

	final double b_sens = 1.5;
	final double b_max = 0.17;

	final double tape_sens = 0.6;
	final double tape_max = 0.15;

	private static final String auto_server_url = "roborio-6731-frc.local";
	private static final int auto_server_port = 1000;

	private void run_auto_move(ArrayList<double[]> segments, ArrayList<Integer> indices) {
		if(segments.size() == indices.size() && this.isRunning()) {
			CommandGroup group = new CommandGroup();
			for(int i = 0; i < segments.size(); i++) {
				double[] d = segments.get(i);
				Network n = Robot.getNetwork(indices.get(i));
				if(n != null) {
					group.addSequential(new NetworkCommand(n, d[0], d[1], d[2], 0.2, 5000, true));
				}
			}
			group.start();
		}
	}

	private Socket create_auto_socket(Socket prev_socket) {
		if(prev_socket != null) {
			try {
				prev_socket.close();
			} catch (Exception e) {
			}
		}
		Socket socket = null;
		try {
			socket = new Socket(auto_server_url, auto_server_port);
		} catch (Exception e) {
			System.out.println("Error creating auto move socket: " + e.getMessage());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
		}
		return socket;
	}

	private void listen() {
		auto_thread = new Thread(() -> {
			Socket socket = create_auto_socket(null);
			while(!Thread.interrupted()) {
				while(!Thread.interrupted() && (socket == null || !socket.isConnected())) {
					socket = create_auto_socket(socket);
				}

				System.out.println("Auto move socket connected");

				while(!Thread.interrupted()) {
					if(!socket.isConnected()) {
						System.out.println("Auto move socket disconnected!");
						break;
					}

					BufferedReader input;
					try {
						input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					} catch (Exception e) {
						System.out.println("Error creating auto move socket reading devices: " + e.getMessage());
						try {
							Thread.sleep(500);
						} catch (InterruptedException ie) {
						}
						continue;
					}
					
					String line;
					while(!Thread.interrupted()) {
						try {
							line = input.readLine();
						} catch(Exception e) {
							System.out.println("Error reading auto move stream: " + e.getMessage());
							continue;
						}

						if(line != null) {
							Scanner scanner;
							try {
								scanner = new Scanner(line);
							} catch (Exception e) {
								System.out.println("Error creating auto move scanner: " + e.getMessage());
								continue;
							}

							try {
								int num_segments = scanner.nextInt();
								ArrayList<double[]> segments = new ArrayList<double[]>();
								ArrayList<Integer> indices = new ArrayList<Integer>();
								for(int i = 0; i < num_segments; i++) {
									double x = scanner.nextDouble();
									double y = scanner.nextDouble();
									double a = scanner.nextDouble();
									int idx = scanner.nextInt();

									segments.add(new double[] {x, y, a});
									indices.add(idx);
								}

								run_auto_move(segments, indices);
							} catch (Exception e) {
								System.out.println("Error reading auto move scanner: " + e.getMessage());
							}
						}
					}
				}
			}
			if(socket != null) {
				try {
					socket.close();
				} catch (Exception e) {
				}
			}
		});
		auto_thread.start();
	}

	@Override
	protected void initialize() {
		Robot.newdrivetrain.stop();
		listen();
	}

	@Override
	// Attempt to slowly increment and decrement forward and rotation inputs to motors to prevent robot from tipping when accelerating quickly
	// On old robot, showed some effect, but could not prevent backheavy robot from nearly tipping when quickly stopping in reverse.
	// When the robot was driven with some caution and at reasonable sensitivities, the code appeared unnecessary, so it is not currently implemented.
	protected void execute() {
		//forward += clamp((OI.getForward() - forward) * updaterate, -maxupdaterate, maxupdaterate);
		//rotation += clamp((OI.getRotation() - rotation) * updaterate, -maxupdaterate, maxupdaterate);
		
		/*
		joyforw = OI.getForward();
		joyrot = OI.getRotation();
		if (counter == 0 || nextforward > 0 && joyforw < nextforward || nextforward < 0 && joyforw > nextforward) {
			forward = joyforw/2;
			counter++;
		}
		else if(joyforw < 0 && nextforward - updaterate >= joyforw)
			forward = nextforward - updaterate;
		else if(joyforw > 0 && nextforward + updaterate <= joyforw)
			forward = nextforward + updaterate;
		else
			forward = joyforw;
		if (counter1 == 0 || Math.abs(joyrot) < Math.abs(nextrotation)) {
			rotation = joyrot/2;
			counter++;
		}
		else if(joyrot < 0 && nextrotation - updaterate >= joyrot)
			rotation = nextrotation - updaterate;
		else if(nextrotation + updaterate <= joyrot)
			rotation = nextrotation + updaterate;
		else
			rotation = joyrot;
		
		//Robot.drivetrain.curvatureDrive(forward, OI.getRotation());
		*/

		/*double[] auto_v = SmartDashboard.getNumberArray("auto_move", new double[] {0.0});
		if(auto_v.length == 3) {
			double x = auto_v[0];
			double y = auto_v[1];
			double a = auto_v[2];
			SmartDashboard.delete("auto_move");
			(new NetworkCommand(x, y, a, 0.2, 5000, true)).start();
		}*/

		double forward = Robot.smoothAccel(OI.getForward(), f_start_time, f_warmup, f_sens, f_pow);
		double rotation = 0.0;
		if(forward > 0.3)
			rotation = Robot.smoothAccel(OI.getRotation(), r_start_time, r_warmup, r_sens, r_pow);
		else
			rotation = Robot.smoothAccel(OI.getRotation(), r_start_time, r_warmup, r_sens + 0.3, r_pow);

		if(OI.getBallAdjustButton()) {
			double b = SmartDashboard.getNumber("ball_x|PI_2", -2.0);
			if(b < -1.0) {
				b = 0.0;
			} else {
				rotation = Robot.clamp(b * b_sens, -b_max, b_max);
			}
		}
		else if(OI.getTapeAdjustButton()) {
			double tape = SmartDashboard.getNumber("tapes|PI_1", -2.0);	
			if(tape < -1.0) {
		   		tape = 0.0;
			} else {
				rotation = Robot.clamp(tape * tape_sens, -tape_max, tape_max);
			}
		}

		Robot.newdrivetrain.curvatureDrive(forward, rotation);
		//nextforward = forward;
		//nextrotation = rotation;
		
		//Robot.drivetrain.curvatureDrive(OI.getForward(), OI.getRotation());
		//System.out.println(Robot.newdrivetrain.getrightdistance());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.newdrivetrain.stop();
		if(auto_thread != null) {
			auto_thread.interrupt();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.newdrivetrain.stop();
	}
}
