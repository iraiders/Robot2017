package org.usfirst.frc.team2713;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2713.commands.AutonomousCommand;
import org.usfirst.frc.team2713.subsystems.ClimbSubsystem;
import org.usfirst.frc.team2713.subsystems.DriveSubsystem;

public class Robot extends IterativeRobot {
	private static Robot robotInstance;
	private static OI oi;
	
	private DriveSubsystem drive;
	private ClimbSubsystem climb;
	
	Command autonomousCommand = new AutonomousCommand();
	
	@Override
	public void robotInit() {
		robotInstance = this;
		
		initSubsystems();

		oi = new OI();

		initCamera();
		initDash();
	}
	
	@Override
	public void disabledInit() {
		drive.resetEncoders();
	}
	
	@Override
	public void autonomousInit() {
		if (autonomousCommand != null) autonomousCommand.start();
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		if (autonomousCommand != null) autonomousCommand.cancel();
		drive.startTeleop();
		climb.startClimb();
	}
	
	private void initSubsystems() {
		drive = new DriveSubsystem();
		climb = new ClimbSubsystem();
	}
	
	private void initDash() {
		RobotMap.OIDriveMode.addDefault("Tank Drive", DriveSubsystem.DriveModes.tank);
		RobotMap.OIDriveMode.addObject("Arcade Drive", DriveSubsystem.DriveModes.arcade);
		RobotMap.OIDriveMode.addObject("Therian Drive", DriveSubsystem.DriveModes.therian);
		RobotMap.OIDriveMode.addObject("Rocket League", DriveSubsystem.DriveModes.rocketleague);
		SmartDashboard.putData("OI Mode", RobotMap.OIDriveMode);
	}
	
	private void initCamera() {
		CameraServer cs = CameraServer.getInstance();
		cs.startAutomaticCapture();
	}
	
	public DriveSubsystem getDrive() {
		return drive;
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public static Robot getRobot() {
		return robotInstance;
	}
	
	public static OI getOI() {
		return oi;
	}
}
