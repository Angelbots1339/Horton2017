package org.usfirst.frc.team1339.robot.commands;

public class DriveIntake extends CommandBase {
	
	private double speed = 0;
	
    public DriveIntake() {
    	requires(intake);
    }

	protected void initialize() {
		// TODO Auto-generated method stub
		
	}
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	if (oi.getRightBumper().get()){
    		speed = -0.8;
    	}
    	else if(oi.getLeftBumper().get()){
    		speed = 0.8;
    	}
    	else{
    		speed = 0;
    	}
    	intake.intake(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
