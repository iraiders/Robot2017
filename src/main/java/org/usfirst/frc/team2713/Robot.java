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
import org.usfirst.frc.team2713.subsystems.GateSubsystem;
import org.usfirst.frc.team2713.subsystems.HighGoalSubsystem;

public class Robot extends IterativeRobot {
	private static Robot robotInstance;
	private static OI oi;

	private DriveSubsystem drive;
	private ClimbSubsystem climb;
	private GateSubsystem gate;
	private HighGoalSubsystem highGoal;

	Command autonomousCommand;

	@Override
	public void robotInit() {
		robotInstance = this;
		oi = new OI();

		initSubsystems();
		initCamera();
		initDash();
	}

	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
		drive.resetEncoders();
	}

	@Override
	public void autonomousInit() {
		autonomousCommand = new AutonomousCommand(RobotMap.startingPosition.getSelected(),
				RobotMap.autonomousActivation.getSelected());
		autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null) autonomousCommand.cancel();
		drive.startTeleop();
		climb.initClimb();
		gate.initGate();
		highGoal.initHighGoal();
	}

	private void initSubsystems() {
		drive = new DriveSubsystem();
		climb = new ClimbSubsystem();
		gate = new GateSubsystem();
		highGoal = new HighGoalSubsystem();
	}

	private void initDash() {
		RobotMap.OIDriveMode.addDefault("Bradford Drive", DriveSubsystem.DriveModes.BRADFORD);
		RobotMap.OIDriveMode.addObject("Tank Drive", DriveSubsystem.DriveModes.TANK);
		RobotMap.OIDriveMode.addObject("Arcade Drive", DriveSubsystem.DriveModes.ARCADE);
		RobotMap.OIDriveMode.addObject("Rocket League", DriveSubsystem.DriveModes.ROCKETLEAGUE);
		SmartDashboard.putData("OI Mode", RobotMap.OIDriveMode);

		RobotMap.autonomousActivation.addDefault("Do something", true);
		RobotMap.autonomousActivation.addObject("Do nothing", false);
		SmartDashboard.putData("Autonomous Activation", RobotMap.autonomousActivation);

		RobotMap.startingPosition.addDefault("Red Position 1 (left)", 1);
		RobotMap.startingPosition.addObject("Red Position 2 (middle)", 2);
		RobotMap.startingPosition.addObject("Red Position 3 (right)", 3);
		RobotMap.startingPosition.addObject("Blue Position 1 (left)", 4);
		RobotMap.startingPosition.addObject("Blue Position 2 (middle)", 5);
		RobotMap.startingPosition.addObject("Blue Position 3 (right)", 6);
		SmartDashboard.putData("Starting Position", RobotMap.startingPosition);

		SmartDashboard.putNumber("Launcher Speed", 0.67D);
		
		SmartDashboard.putNumber("Speed", )
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
