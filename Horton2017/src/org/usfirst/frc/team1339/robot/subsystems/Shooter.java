package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem{
	
	private CANTalon shooterMotorOne = new CANTalon(RobotMap.kShooterMotorOne);
	private CANTalon shooterMotorTwo = new CANTalon(RobotMap.kShooterMotorTwo);
	
	public Shooter(){
	}
	
	public void initDefaultCommand() {
		//setDefaultCommand(new DriveShooter());
	}
	
	public void shoot(double speed){
		shooterMotorOne.set(speed);
		shooterMotorTwo.set(-speed);
	}
}
