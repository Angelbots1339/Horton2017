package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class MaxAcceleration extends CommandBase{

	private double initTime;
	
	public MaxAcceleration(){
		requires(Robot.chassis);
		setRunSpeed(0.05);
		setTimeout(7);
	}
	
	protected void init() {
		initTime = Timer.getFPGATimestamp();
	}

	public void execute() {
		if(Timer.getFPGATimestamp() < initTime + 1){
			Robot.chassis.setMotorValues(1, 1);
		} else{
			Robot.chassis.setMotorValues(0, 0);
		}
		Robot.chassis.calculate();
	}

	public boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
		Robot.chassis.getAvgAcc();
		Robot.chassis.setMotorValues(0, 0);
	}

	protected void interrupted() {
		Robot.chassis.getAvgAcc();
		Robot.chassis.setMotorValues(0, 0);
	}

}
