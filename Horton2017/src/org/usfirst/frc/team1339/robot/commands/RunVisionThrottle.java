package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunVisionThrottle extends CommandBase{
	Joystick stick;
	double throttle;
	
	public RunVisionThrottle(){
		requires(chassis);
	}
	
	protected void initialize(){
		vision.start();
		chassis.visionPID.setSetpoint(160);
	}
	
	public void execute(){
		int centerX = vision.getCenterX();
		SmartDashboard.putNumber("CENTERX", centerX);
		
		stick = oi.getXboxStick();
		throttle = stick.getRawAxis(RobotMap.xboxLeftYAxis);
		chassis.runVisionPIDThrottle(centerX, throttle);
	}
	
	public boolean isFinished(){
		return false;
	}
	
	protected void end(){
		vision.stop();
	}
	
	protected void interrupted(){
		vision.stop();
	}
}
