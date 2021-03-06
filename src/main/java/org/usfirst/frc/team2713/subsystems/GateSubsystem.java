package org.usfirst.frc.team2713.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2713.OI;
import org.usfirst.frc.team2713.Robot;
import org.usfirst.frc.team2713.RobotMap;
import org.usfirst.frc.team2713.commands.GateCommand;

public class GateSubsystem extends Subsystem {
	public Servo lowServo = new Servo(RobotMap.LOW_GATE_SERVO);
	public Servo highServo = new Servo(RobotMap.HIGH_GATE_SERVO);

	private int openDegree = 90;
	private int closedDegree = 0;

	public void initGate() {
		JoystickButton highOpen = new JoystickButton(Robot.getOI().getController(OI.ControllerType.fight), 6);
		JoystickButton highClose = new JoystickButton(Robot.getOI().getController(OI.ControllerType.fight), 2);
		JoystickButton lowOpen = new JoystickButton(Robot.getOI().getController(OI.ControllerType.fight), 5);
		JoystickButton lowClosed = new JoystickButton(Robot.getOI().getController(OI.ControllerType.fight), 1);

		highOpen.whileActive(new GateCommand(this, highServo, openDegree));
		highClose.whileActive(new GateCommand(this, highServo, closedDegree));
		lowOpen.whileActive(new GateCommand(this, lowServo, openDegree));
		lowClosed.whileActive(new GateCommand(this, lowServo, closedDegree));
	}

	@Override
	protected void initDefaultCommand() {
	}
}
