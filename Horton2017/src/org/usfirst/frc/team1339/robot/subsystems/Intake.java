package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem{

	public static CANTalon axleMotor = new CANTalon(RobotMap.kAxleMotor);
	
	public Intake(){
	}
	
	public void initDefaultCommand() {
		//setDefaultCommand(new DriveIntake());
	}
	
	public void intake(double speed) {
		axleMotor.set(speed);
    }
}
