package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.Timer;

public class MaxAcceleration extends CommandBase{

	private double initTime;
	
	public MaxAcceleration(){
		requires(chassis);
		setTimeout(7);
	}
	
	protected void initialize() {
		initTime = Timer.getFPGATimestamp();
	}

	public void execute() {
		if(Timer.getFPGATimestamp() < initTime + 1){
			chassis.setMotorValues(1, 1);
		} else{
			chassis.setMotorValues(0, 0);
		}
		chassis.calculate();
	}

	public boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
		chassis.getAvgAcc();
		chassis.setMotorValues(0, 0);
	}

	protected void interrupted() {
		chassis.getAvgAcc();
		chassis.setMotorValues(0, 0);
	}

}
