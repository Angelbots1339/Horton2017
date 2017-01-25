package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class DriveShooterTimeout extends CommandBase {
	
	private double m_speed;
	
    public DriveShooterTimeout(double speed, double timeout) {
    	setTimeout(timeout);
    	m_speed = speed;
    }

	protected void initialize() {
		
	}
    
    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	shooter.shoot(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end(){
    	shooter.shoot(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	shooter.shoot(0);
    }

}
