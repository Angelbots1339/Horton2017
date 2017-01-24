package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.base.CommandBase;
import org.usfirst.frc.team1339.robot.Robot;
import org.usfirst.frc.team1339.utils.Constants;

/**
 *
 */
public class DriveShooter extends CommandBase {
	
	private double speed = 0;
	
    public DriveShooter() {
    	requires(Robot.shooter);
    }

	protected void init() {
		
	}
    
    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	double leftTrigger = oi.getXboxStick().getRawAxis(Constants.xboxLeftTrigger);
    	double rightTrigger = oi.getXboxStick().getRawAxis(Constants.xboxRightTrigger);

    	speed = rightTrigger - leftTrigger;
    	
    	Robot.shooter.shoot(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end(){
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
