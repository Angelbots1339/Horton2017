package org.usfirst.frc.team1339.robot.commands;

public class GyroPID extends CommandBase{

	double m_goal;
	
	public GyroPID(double goal){
		requires(chassis);
		m_goal = goal;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		chassis.GyroPID.setSetpoint(chassis.spartanGyro.getAngle() + m_goal);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		chassis.gyroPID();
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return chassis.GyroPID.onTarget(10);
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		chassis.setMotorValues(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		chassis.setMotorValues(0, 0);
	}

}
