package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunVision extends CommandBase{
	public double lastsTime=0;
	public RunVision(){
		requires(chassis);
	}
	
	protected void initialize(){
		vision.start();
		chassis.visionPID.setSetpoint(160);
	}
	
	public void execute(){
		double time = Timer.getFPGATimestamp();
		System.out.println(time-lastsTime);
		lastsTime = time;
		
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
