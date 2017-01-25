package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunVision extends CommandBase{
	
	public RunVision(){
		requires(chassis);
	}
	
	protected void initialize(){
		vision.start();
		chassis.GyroPID.setSetpoint(160);
	}
	
	public void execute(){
		int centerX = vision.getCenterX();
		SmartDashboard.putNumber("CENTERX", centerX);
		chassis.runVisionPid(centerX);
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
