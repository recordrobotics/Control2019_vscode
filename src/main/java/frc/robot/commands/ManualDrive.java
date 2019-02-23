package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ManualDrive extends Command {
	public ManualDrive() {
		//requires(Robot.drivetrain);
		requires(Robot.newdrivetrain);
	}

	double forward;
	double rotation;
	final double updaterate = 0.1;
	final double maxupdaterate = 0.05;

	private double clamp(double x, double l, double u) {
		return Math.max(l, Math.min(x, u));
	}

	@Override
	protected void initialize() {
		Robot.newdrivetrain.stop();
		forward = 0;
		rotation = 0;
	}

	@Override
	// Attempt to slowly increment and decrement forward and rotation inputs to motors to prevent robot from tipping when accelerating quickly
	// On old robot, showed some effect, but could not prevent backheavy robot from nearly tipping when quickly stopping in reverse.
	// When the robot was driven with some caution and at reasonable sensitivities, the code appeared unnecessary, so it is not currently implemented.
	protected void execute() {
		forward += clamp((OI.getForward() - forward) * updaterate, -maxupdaterate, maxupdaterate);
		rotation += clamp((OI.getRotation() - rotation) * updaterate, -maxupdaterate, maxupdaterate);

		/*joyforw = OI.getForward();
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
			forward = joyforw;*/
			/*
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
		Robot.newdrivetrain.curvatureDrive(forward, rotation);
		//nextforward = forward;
		//nextrotation = rotation;
		
		//Robot.drivetrain.curvatureDrive(OI.getForward(), OI.getRotation());
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
