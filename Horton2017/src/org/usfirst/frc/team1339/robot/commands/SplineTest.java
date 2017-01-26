package org.usfirst.frc.team1339.robot.commands;

public class SplineTest extends CommandBase{

	double m_radius, m_angle, m_startVel;
	boolean m_direction;
	
	public SplineTest(double radius, double angle, double startVel, boolean direction) {
		// TODO Auto-generated constructor stub
		requires(chassis);
		m_radius = radius;
		m_angle = Math.toRadians(angle);
		m_direction = direction;
		m_startVel = startVel;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub\
		//oi.GyroPID.setSetpoint(Math.toDegrees(m_angle));
		chassis.chassisSP.configureSplineProfile(m_radius, m_angle, m_startVel, m_direction);
		chassis.chassisSP.initializeProfile(chassis.leftEncoder.get(), chassis.rightEncoder.get(),
				chassis.spartanGyro.getAngle());
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if(!chassis.chassisSP.isFinishedTrajectory()){
			chassis.splineProfile();
		}
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return chassis.chassisSP.isFinishedTrajectory();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		//chassis.gyroPID();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		chassis.setMotorValues(0, 0);
	}

}
