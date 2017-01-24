package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;

public class DriveStraight extends CommandBase{
	
	private double m_speed;

	public DriveStraight(double speed, double timeout){
		requires(Robot.chassis);
		m_speed = speed;
		setRunSpeed(0.05);
		setTimeout(timeout);
	}
	
	protected void init() {
		// TODO Auto-generated method stub
	}

	public void execute() {
		// TODO Auto-generated method stub
		Robot.chassis.setMotorValues(m_speed, m_speed);
	}

	public boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut(); 
	}

	protected void end() {
		// TODO Auto-generated method stub
		Robot.chassis.setMotorValues(0, 0);
	}

	protected void interrupted() {
		// TODO Auto-generated method stub
		Robot.chassis.setMotorValues(0, 0);
	}

}
