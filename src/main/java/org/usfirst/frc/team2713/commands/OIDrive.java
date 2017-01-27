package org.usfirst.frc.team2713.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2713.Robot;
import org.usfirst.frc.team2713.RobotMap;
import org.usfirst.frc.team2713.subsystems.DriveSubsystem;

public class OIDrive extends Command {
	private RobotDrive roboDrive;
	DriveSubsystem drive;
	XboxController xbox = Robot.getOI().getController();
	private double scaler = 1.0;
	private double deadband = 0.1;
	private double polarity = -1;

	public OIDrive(DriveSubsystem drive, RobotDrive roboDrive) {
		this.drive = drive;
		this.roboDrive = roboDrive;
		requires(drive);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		SmartDashboard.putString("encoders", "topLeft: " + drive.topLeft.getEncPosition() + " & " + drive.topLeft.getEncVelocity() + "\ntopRight: " + drive.topRight.getEncPosition() + " & " + drive.topRight.getEncVelocity());

		if (RobotMap.OIDriveMode.getSelected() == DriveSubsystem.DriveModes.tank){
			drive.tankDrive(xbox.getY(Hand.kLeft)*scaler*polarity, xbox.getY(Hand.kRight)*scaler*polarity, deadband, true);
		} else if (RobotMap.OIDriveMode.getSelected() == DriveSubsystem.DriveModes.ryanDrive){
			double speed = drive.getDeadband(xbox.getY(Hand.kLeft));
			double rotation = drive.getDeadband(xbox.getX(Hand.kRight));
			drive.arcadeDrive(speed*scaler*polarity, rotation*scaler*polarity, deadband, false);
		}
	}

	@Override
	protected boolean isFinished(){ return false; }

	@Override
	protected void end(){}

	@Override
	protected void interrupted(){}
}
