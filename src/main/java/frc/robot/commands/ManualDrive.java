package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualDrive extends Command {
	public ManualDrive() {
		//requires(Robot.drivetrain);
		requires(Robot.newdrivetrain);

	}

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

	@Override
	protected void initialize() {
		Robot.newdrivetrain.stop();
	}

	private double clamp(double v, double min, double max) {
		return Math.max(min, Math.min(max, v));
	}

	@Override
	// Attempt to slowly increment and decrement forward and rotation inputs to motors to prevent robot from tipping when accelerating quickly
	// On old robot, showed some effect, but could not prevent backheavy robot from nearly tipping when quickly stopping in reverse.
	// When the robot was driven with some caution and at reasonable sensitivities, the code appeared unnecessary, so it is not currently implemented.
	protected void execute() {
		//forward += clamp((OI.getForward() - forward) * updaterate, -maxupdaterate, maxupdaterate);
		//rotation += clamp((OI.getRotation() - rotation) * updaterate, -maxupdaterate, maxupdaterate);
		double rotation;
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

		double forward = Robot.smoothAccel(OI.getForward(), f_start_time, f_warmup, f_sens, f_pow);
		if(forward > 0.3)
			rotation = Robot.smoothAccel(OI.getRotation(), r_start_time, r_warmup, r_sens, r_pow);
		else
			rotation = Robot.smoothAccel(OI.getRotation(), r_start_time, r_warmup, r_sens + 0.3, r_pow);

		if(OI.getBallAdjustButton()) {
			double b = SmartDashboard.getNumber("ball_x|PI_2", -2.0);
			if(b < -1.0) {
				b = 0.0;
			} else {
				rotation = clamp(b * b_sens, -b_max, b_max);
			}
		}
		else if(OI.getTapeAdjustButton()) {
			double tape = SmartDashboard.getNumber("tapes|PI_1", -2.0);	
			if(tape < -1.0) {
		   		tape = 0.0;
			} else {
				rotation = clamp(tape * tape_sens, -tape_max, tape_max);
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.newdrivetrain.stop();
	}
}
