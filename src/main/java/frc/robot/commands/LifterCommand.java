package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;

public class LifterCommand extends Command {
	// Toggled button to switch from automatic or manual lift. Not currently used because automatic and manual lift use different buttons.
	// Magnetic switches
	boolean switch0;
	boolean top_switch;
	// Standard lift positions
	double[] auto_positions;
	// Joystick inputs
	boolean manualraise;
	boolean manuallower;
	boolean manualrelease;
	int buttons;
	boolean pieceAdjustPressed, pieceAdjustReleased, tapeAdjustReleased;
	//final static double manualupdaterate = 0.01;

	int reset = 0;
	boolean doReset = false;
	double movement = 0;

	private final double ballPos = 0.1;
	private final double hatchPos = 0.2;
	private final double hatchCatchPos = 0.3;


	int auto_position_index = 0;

	public LifterCommand(boolean r) {
		requires(Robot.lifter);
		doReset = r;
	}
	@Override
	protected void initialize() {
		Robot.lifter.getPIDController().setEnabled(!doReset);
		Robot.lifter.setLift(0);
		reset = doReset ? 1 : 0;
		Robot.lifter.setSetpoint(Robot.lifter.getlifterpos());
	}
	@Override
	protected void execute() {
		// Input to the motors will be set at 0 if none of the conditions are met
		movement = 0;
		switch0 = Robot.lifter.get0switch();
		top_switch = Robot.lifter.get1switch() && Robot.lifter.get2switch();
		manualraise = OI.getManualRaiseButton();
		manuallower = OI.getManualLowerButton();
		manualrelease = OI.getManualRelease();
		auto_positions = Robot.lifter.getAutoPositions();
		buttons = OI.getButtons();
		pieceAdjustPressed = OI.getPieceAdjustPressed();
		pieceAdjustReleased = OI.getPieceAdjustReleased();
		tapeAdjustReleased = OI.getTapeAdjustReleased();
		// Reset the lift back to default position, and reset encoder values if necessary
		if(reset == 1) {
			if(!switch0) {
				movement = -1.0;
				Robot.lifter.setLift(movement);
				//System.out.println("down");
			}
			else {
				reset = 0;
				Robot.lifter.encoderReset();
				Robot.lifter.setLift(0.0);
				Robot.lifter.getPIDController().setEnabled(true);
				Robot.lifter.setSetpoint(0.0);
			}			
		}
		else if(switch0) {
			Robot.lifter.encoderReset();
		}

		System.out.println("buttons: " + buttons);
		//Auto automatically raises/lowers to the next available standard position
		if(reset == 0) {
			if(buttons >= 1 && buttons <= 6) { // Either but not both raise and lower must be activated
				Robot.lifter.setSetPoint(auto_positions[buttons - 1]);
			}
			// Manual does not use stages
			if((manualraise || manuallower) && !(manualraise && manuallower)) {
				Robot.lifter.getPIDController().setEnabled(false);
				if(manualraise && !top_switch) {
					movement = 1;
				}
				else if(manuallower && !switch0) {
					movement = -1;
				}
				Robot.lifter.setLift(movement);
			}
			else if(pieceAdjustPressed) {
				if(Robot.goingForBalls) {
					Robot.lifter.setSetpoint(ballPos);
				} else {
					Robot.lifter.setSetpoint(hatchPos);
				}
			}
			else if(pieceAdjustReleased && !Robot.goingForBalls) {
				Robot.lifter.setSetpoint(hatchCatchPos);
			}
			else if(tapeAdjustReleased && !Robot.goingForBalls) {
				Robot.lifter.setSetpoint(Robot.lifter.getSetpoint() + hatchPos - hatchCatchPos);
			}

			if (manualrelease) {
				Robot.lifter.setLift(0.0);
				Robot.lifter.getPIDController().setEnabled(true);
				Robot.lifter.setSetPoint(Robot.lifter.getlifterpos());
			}
			
			SmartDashboard.putBoolean("liftercommand.pidenabled", Robot.lifter.getPIDController().isEnabled());
			SmartDashboard.putNumber("liftercommand.lifterpos", Robot.lifter.getlifterpos());
			SmartDashboard.putNumber("liftercommand.setpoint", Robot.lifter.getSetpoint());
			SmartDashboard.putNumber("liftercommand.movement", movement);
		}
		//Robot.lifter.setSetpoint(Robot.lifter.getSetPoint() + manualupdaterate*movement);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.lifter.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.lifter.stop();
	}
}
