package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class AcquisitionCommand extends Command {
	public AcquisitionCommand(/*double rv*/) {
		//reset_vel = rv;
		requires(Robot.acquisition);
	}
	// Joystick inputs
	int raisebutton;
	int lowerbutton;
	double rollbutton;
	double slowrollbutton;
	boolean raisebuttonpressed;
	boolean lowerbuttonpressed;
	boolean release;
	// Switches
	boolean switch0; 
	boolean switch1;
	boolean pieceAdjustPressed, pieceAdjustReleased, pieceAdjust, tapeAdjust, tapeAdjustReleased, tapeAdjustPressed;
	// Encoders
	double acquisitionpos;
	double liftpos;
	long start_t = 0;
	long middle_t = 0;

	double movement;
	int reset;
	//double rate = 0;
	//boolean lockraise;
	
	final static double updaterate = 0.04;

	private double reset_vel = 0.3;
	private final double hatchPos = 1.0;
	private final double ballPickupPos = 1.0;
	private final double ballHoldPos = 0.8;
	private final double ballDepositPos = 1.1;
	
	private long release_time;
	private final long release_spin_time = 600;

	public final static double threshHeight = 0.5;
	public final static double safePos = 1.0;

	@Override
	protected void initialize() {
		/*Robot.acquisition.stop();
		Robot.acquisition.getPIDController().setEnabled(false);
		reset = 1;
		start_t = System.currentTimeMillis();
		middle_t = 0;
		rangeOffset = 0.0;
		SmartDashboard.putNumber("acquisitioncommand.lowerRange", rangeOffset);
		Robot.acquisition.getPIDController().setInputRange(rangeOffset, 1.2);*/
		Robot.acquisition.roll(0.0);
		release_time = 0;
	}
	@Override
	protected void execute() {
		movement = 0;
		liftpos = Robot.lifter.getlifterpos();
		acquisitionpos = Robot.acquisition.getacquisitionpos();
		raisebutton = -1*(OI.getPivotRaiseButton() ? 1 : 0);
		lowerbutton = OI.getPivotLowerButton() ? 1 : 0;
		raisebuttonpressed = OI.getPivotRaiseButtonPressed();
		lowerbuttonpressed = OI.getPivotLowerButtonPressed();
		rollbutton = OI.getRollButton();
		slowrollbutton = OI.getSlowRollButton();
		switch0 = Robot.acquisition.getswitch0();
		switch1 = Robot.acquisition.getswitch1();

		boolean prevPieceAdjust = pieceAdjust;
		boolean prevTapeAdjust = tapeAdjust;
		pieceAdjust = OI.getPieceAdjustButton();
		tapeAdjust = OI.getTapeAdjustButton();

		pieceAdjustPressed = pieceAdjust && !prevPieceAdjust;
		pieceAdjustReleased = !pieceAdjust && prevPieceAdjust;
		tapeAdjustReleased = !tapeAdjust && prevTapeAdjust;
		tapeAdjustPressed = tapeAdjust && !prevTapeAdjust;

		//release = OI.getAcquisitionRelease();
		// Reset the lift back to default position, and reset encoder values if necessary
		
		if(Robot.usePivot()) {
			if(OI.getRangeButtonPressed()) {
				Robot.acquisition.incrementOffset(0.4);
			}

			if(OI.getRangeButton2Pressed()) {
				Robot.acquisition.incrementOffset(-0.4);
			}

			if(reset == 1) {
				if(switch0) {
					reset = 0;
					Robot.acquisition.encoderReset();
					Robot.acquisition.stop();
					Robot.acquisition.getPIDController().setEnabled(true);
					Robot.acquisition.setSetpoint(0.0);
				}
				else {
					if(System.currentTimeMillis() - start_t < 500)
						Robot.acquisition.rotate(0.2);
					else if(middle_t > 0) {
						if(System.currentTimeMillis() - middle_t > 300) {
							reset = 0;
							Robot.acquisition.encoderReset();
							Robot.acquisition.stop();
							Robot.acquisition.getPIDController().setEnabled(true);
							Robot.acquisition.setSetpoint(0.0);
						}
					}
					else {
						if(System.currentTimeMillis() - start_t > 1500) { 
							middle_t = System.currentTimeMillis();
							Robot.acquisition.rotate(0.0);
						}
						else {
							Robot.acquisition.rotate(-reset_vel);
						}
					}
				}
			}
			else {
				if(switch0) {
					Robot.acquisition.encoderReset();
					acquisitionpos = Robot.acquisition.getacquisitionpos();
				}

				if(raisebutton+lowerbutton != 0) {
					movement += raisebutton;
					movement += lowerbutton;
					double tempupdaterate = updaterate;
					if(raisebuttonpressed || lowerbuttonpressed)
						tempupdaterate *= 3;
				 	double pos = Robot.acquisition.getSetPoint() + tempupdaterate*movement;
					
					Robot.acquisition.setSetpoint(pos);
				}
				else if(Robot.adjustGrabber()) {
					if (pieceAdjustReleased && Robot.goingForBalls()) {
						Robot.acquisition.setSetpoint(ballHoldPos);
					}
					else if(pieceAdjustPressed) {
						if(Robot.goingForBalls()) {
							Robot.acquisition.setSetpoint(ballPickupPos);
						} else {
							Robot.acquisition.setSetpoint(hatchPos);
						}
					}
					else if(tapeAdjustPressed) {
						if(Robot.goingForBalls()) {
							int p = Robot.lifter.getAutoPos();
							if(p == -1) {
								Robot.acquisition.setSetpoint(ballDepositPos);
							} else {
								Robot.acquisition.setDepositPos(p);
							}
						} else {
							Robot.acquisition.setSetpoint(hatchPos);
						}
					}
				}

				if(Robot.lifter.getlifterpos() <= threshHeight && Robot.acquisition.getSetpoint() > safePos) {
					Robot.acquisition.setSetpoint(safePos);
				}
			}
		}

		if(Robot.adjustGrabber() && tapeAdjustReleased && Robot.goingForBalls()) {
			release_time = System.currentTimeMillis();
		}

		double roll = (pieceAdjust && Robot.goingForBalls()) ? -0.7 : 0.0;

		if(System.currentTimeMillis() - release_time < release_spin_time) {
			roll = 1.0;
		}

		SmartDashboard.putNumber("acquisitionCommand.setpoint", Robot.acquisition.getSetpoint());
		// Can roll in two directions based on which roll button is pressed
		
		if(slowrollbutton != 0.0)
			roll = 0.4 * slowrollbutton;
		else if(rollbutton > 0.0)
			roll = rollbutton;
		else if(rollbutton < 0.0)
			roll = rollbutton;
		
		Robot.acquisition.roll(roll);
		// Raise button and lower button are not pushed at the same time, and one is active	
		//Robot.acquisition.rotate(-0.5);
		//rate = Robot.acquisition.getEncoderRate();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		//Robot.acquisition.setSetpoint(Robot.acquisition.getacquisitionpos());
		Robot.acquisition.roll(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		//Robot.acquisition.setSetpoint(Robot.acquisition.getacquisitionpos());
		Robot.acquisition.roll(0.0);
	}
}
