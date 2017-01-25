package org.usfirst.frc.team1339.robot.commands;

public class DriveStraight extends CommandBase{
	
	private double m_speed;

	public DriveStraight(double speed, double timeout){
		requires(chassis);
		m_speed = speed;
		setTimeout(timeout);
	}
	
	protected void initialize() {
		// TODO Auto-generated method stub
	}

	public void execute() {
		// TODO Auto-generated method stub
		chassis.setMotorValues(m_speed, m_speed);
	}

	public boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut(); 
	}

	protected void end() {
		// TODO Auto-generated method stub
		chassis.setMotorValues(0, 0);
	}

	protected void interrupted() {
		// TODO Auto-generated method stub
		chassis.setMotorValues(0, 0);
	}

}
