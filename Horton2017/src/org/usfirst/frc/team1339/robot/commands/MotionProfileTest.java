package org.usfirst.frc.team1339.robot.commands;

public class MotionProfileTest extends CommandBase {
	
	private double m_goal, tolerance, initialLeft, initialRight;
	private int counter;
	
	public MotionProfileTest(double goal, double input_tolerance){
		requires(chassis);
		m_goal = goal;
		tolerance = input_tolerance;
	}

	protected void initialize() {
		// TODO Auto-generated method stub
		chassis.GyroPID.setSetpoint(chassis.spartanGyro.getAngle());
		chassis.ChassisMP.configureNewProfile(m_goal);
		initialLeft = chassis.leftEncoder.get();
		initialRight = chassis.rightEncoder.get();
		chassis.ChassisMP.initializeProfile(initialLeft, initialRight);
	}

	public void execute() {
		// TODO Auto-generated method stub
		//System.out.println("Running");
		chassis.motionProfile();
		if((Math.abs(chassis.leftEncoder.get() - m_goal - initialLeft) < tolerance)
				&& (Math.abs(chassis.rightEncoder.get() - m_goal - initialRight) < tolerance)){
			counter++;
		}
	}

	public boolean isFinished() {
		// TODO Auto-generated method stub
		return chassis.ChassisMP.isFinishedTrajectory() && counter >= 5;
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
