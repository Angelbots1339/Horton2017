package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;

public class MotionProfileTest extends CommandBase {
	
	private double m_goal, tolerance, initialLeft, initialRight;
	private int counter;
	
	public MotionProfileTest(double goal, double input_tolerance){
		requires(Robot.chassis);
		m_goal = goal;
		tolerance = input_tolerance;
	}

	protected void init() {
		// TODO Auto-generated method stub
		oi.GyroPID.setSetpoint(oi.kSpartanGyro.getAngle());
		oi.ChassisMP.configureNewProfile(m_goal);
		initialLeft = oi.getLeftDriveEnc();
		initialRight = oi.getRightDriveEnc();
		oi.ChassisMP.initializeProfile(initialLeft, initialRight);
	}

	public void execute() {
		// TODO Auto-generated method stub
		//System.out.println("Running");
		Robot.chassis.motionProfile();
		if((Math.abs(oi.getLeftDriveEnc() - m_goal - initialLeft) < tolerance)
				&& (Math.abs(oi.getRightDriveEnc() - m_goal - initialRight) < tolerance)){
			counter++;
		}
	}

	public boolean isFinished() {
		// TODO Auto-generated method stub
		return oi.ChassisMP.isFinishedTrajectory() && counter >= 5;
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
