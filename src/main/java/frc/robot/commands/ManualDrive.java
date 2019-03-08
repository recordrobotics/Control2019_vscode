package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualDrive extends Command {
	public ManualDrive() {
		//requires(Robot.drivetrain);
		requires(Robot.newdrivetrain);
	}

	long[] f_start_time = {0};
	long[] r_start_time = {0};

	long f_warmup = 500;
	long r_warmup = 500;
	double f_sens = 0.5;
	double r_sens = 0.2;
	double r_sens2 = 0.5;

	@Override
	protected void initialize() {
		Robot.newdrivetrain.stop();
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

		double forward = Robot.smoothAccel(OI.getForward(), f_start_time, f_warmup, f_sens);
		double rotation = 0.0;
		if(Math.abs(forward) < 0.2)
			rotation = Robot.smoothAccel(OI.getRotation(), r_start_time, r_warmup, r_sens2);
		else
			rotation = Robot.smoothAccel(OI.getRotation(), r_start_time, r_warmup, r_sens);
		
		//System.out.println(OI.getForward());
		Robot.newdrivetrain.curvatureDrive(forward, rotation);
		//nextforward = forward;
		//nextrotation = rotation;
		
		//Robot.drivetrain.curvatureDrive(OI.getForward(), OI.getRotation());
		System.out.println(Robot.newdrivetrain.getrightdistance());
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
