package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends CommandBase{
	
	double left, right;
	Joystick stick;
	
	public TankDrive(){
		requires(chassis);
	}
	
	protected void initialize(){
		
	}
	
	public void execute(){
		stick = oi.getXboxStick();
		left = stick.getRawAxis(RobotMap.xboxLeftYAxis);
		right = stick.getRawAxis(RobotMap.xboxRightYAxis);
		
		chassis.tankDrive(left, right);
	}
	
	public boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
}
