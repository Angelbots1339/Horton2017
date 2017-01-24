package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.utils.Constants;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends CommandBase{
	
	double left, right;
	Joystick stick;
	
	public TankDrive(){
		requires(Robot.chassis);
		setRunSpeed(0.05);
	}
	
	protected void initialize(){
		
	}
	
	public void execute(){
		stick = oi.getXboxStick();
		left = stick.getRawAxis(Constants.xboxLeftYAxis);
		right = stick.getRawAxis(Constants.xboxRightYAxis);
		
		Robot.chassis.tankDrive(left, right);
	}
	
	public boolean isFinished(){
		return false;
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
}
