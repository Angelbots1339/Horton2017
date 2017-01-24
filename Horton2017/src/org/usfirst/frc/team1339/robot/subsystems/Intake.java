package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.base.SubsystemBase;
import org.usfirst.frc.team1339.commands.DriveIntake;
import org.usfirst.frc.team1339.utils.Constants;

import com.ctre.CANTalon;

public class Intake extends SubsystemBase{

	public static CANTalon axleMotor = new CANTalon(Constants.kAxleMotor);
	
	public Intake(){
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveIntake());
	}
	
	public void intake(double speed) {
		axleMotor.set(speed);
    }
}
