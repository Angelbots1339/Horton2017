package org.usfirst.frc.team1339.robot.commands;

public class Chill extends CommandBase{
	
	public Chill(double timeout){
		setTimeout(timeout);
	}
	
	protected void initialize(){
		
	}
	
	protected void execute(){
	}
	
	protected boolean isFinished(){
		return isTimedOut();
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		
	}
}
