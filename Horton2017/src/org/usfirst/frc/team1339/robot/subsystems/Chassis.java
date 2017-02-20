package org.usfirst.frc.team1339.robot.subsystems;

import java.util.ArrayList;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ArcadeDrive;
import org.usfirst.frc.team1339.utils.MotionProfile;
import org.usfirst.frc.team1339.utils.SplineProfile;
import org.usfirst.frc.team1339.utils.SynchronousPID;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends Subsystem{
	
	public SplineProfile chassisSP = new SplineProfile(
			RobotMap.splineMPKp, RobotMap.splineMPKi, RobotMap.splineMPKd,
			RobotMap.splineMPKa, RobotMap.splineMPKv);

	public static CANTalon leftMotorOne = new CANTalon(RobotMap.kLeftMotorOne);
	public static CANTalon leftMotorTwo = new CANTalon(RobotMap.kLeftMotorTwo);
	public static CANTalon rightMotorOne = new CANTalon(RobotMap.kRightMotorOne);
	public static CANTalon rightMotorTwo = new CANTalon(RobotMap.kRightMotorTwo);
	
	public SynchronousPID visionPID = new SynchronousPID(0.005, 0, 0);
	public SynchronousPID drivePID = new SynchronousPID(0.015, 0, 0);
	public SynchronousPID LeftDriveEncoderPID = new SynchronousPID(
			RobotMap.kDriveKp , RobotMap.kDriveKi , RobotMap.kDriveKd);
	public SynchronousPID ShortLeftDriveEncoderPID = new SynchronousPID(
			RobotMap.kShortDriveKp , RobotMap.kShortDriveKi , RobotMap.kShortDriveKd);
	public SynchronousPID RightDriveEncoderPID = new SynchronousPID(
			RobotMap.kDriveKp , RobotMap.kDriveKi , RobotMap.kDriveKd);
	public SynchronousPID ShortRightDriveEncoderPID = new SynchronousPID(
			RobotMap.kShortDriveKp , RobotMap.kShortDriveKi , RobotMap.kShortDriveKd);
	public SynchronousPID TurnGyroPID = new SynchronousPID(
			RobotMap.kTurnKp , RobotMap.kTurnKi , RobotMap.kTurnKd);
	
	public SynchronousPID GyroPID = new SynchronousPID(
			RobotMap.kGyroKp , RobotMap.kGyroKi , RobotMap.kGyroKd);
	public SynchronousPID MotionProfile = new SynchronousPID(
			RobotMap.k_mp_Kp , RobotMap.k_mp_Ki , RobotMap.k_mp_Kd);
	
	//Motion Profiles
	public MotionProfile ChassisMP = new MotionProfile(
			RobotMap.chassisMPKp, RobotMap.chassisMPKi, RobotMap.chassisMPKd, 
			RobotMap.chassisMPKa, RobotMap.chassisMPKv);
	
	//Encoders
	public Encoder rightEncoder = new Encoder(
			RobotMap.kRightDriveAEncoder , RobotMap.kRightDriveBEncoder, true);
	public Encoder leftEncoder = new Encoder(
			RobotMap.kLeftDriveAEncoder , RobotMap.kLeftDriveBEncoder);
	public ADXRS450_Gyro spartanGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	
	double leftLastSpeed, rightLastSpeed;
	double rightSpeed, leftSpeed;
	double rate = 0.085;
	
	private ArrayList<Double> accel = new ArrayList<Double>();
	
	private double lastTime = 0, lastRightSpeed = 0, lastLeftSpeed = 0;
	
	public Chassis(){
		
	}
	
	public void initDefaultCommand(){
		setDefaultCommand(new ArcadeDrive());
	}
	
	public void runVisionPid(int[] centerX){
		double targetSum = 0;
		for(int i = 0; i < centerX.length; i++){
			targetSum += centerX[i];
		}
		double targetAvg = targetSum/centerX.length;
		if(centerX.length == 0){
			setMotorValues(0, 0);
		}
		else{
			double output = visionPID.calculate(targetAvg);
			double left = output;
			double right = -output;
			setMotorValues(left, right);
		}
	}
	
	public void runVisionPIDThrottle(int[] centerX, double throttle){
		double gyroOutput = 0;
		double right = throttle;
		double left = throttle;
		double targetSum = 0;
		for(int i = 0; i < centerX.length; i++){
			targetSum += centerX[i];
		}
		double targetAvg = targetSum/centerX.length;
		if(centerX.length != 0){
			gyroOutput = visionPID.calculate(targetAvg)*0.25;
		}
		right -= gyroOutput;
		left += gyroOutput;
		setMotorValues(left, right);
	}
	
	public void autoGear(int[] centerX, int[] heights){
		double gyroOutput = 0;
		double distOutput = 0;
		double targetSum = 0;
		
		for(int i = 0; i < centerX.length; i++){
			targetSum += centerX[i];
		}
		double targetAvg = targetSum/centerX.length;
		if(centerX.length != 0){
			gyroOutput = visionPID.calculate(targetAvg)*0.5;
		}
		
		targetSum = 0;
		for(int i = 0; i < heights.length; i++){
			targetSum += heights[i];
		}
		targetAvg = targetSum/heights.length;
		if(heights.length != 0){
			distOutput = -drivePID.calculate(targetAvg)*0.5;
		}

		double right = distOutput;
		double left = distOutput;
		right -= gyroOutput;
		left += gyroOutput;
		setMotorValues(left, right);
	}
	
	public void driveWithJoystick(double throttle, double turn){
       	double right = throttle;
    	double left = throttle;
    	double turningThrottleScale;
    	
    	if (java.lang.Math.abs(throttle) > 0.1) {
            turningThrottleScale = java.lang.Math.abs(throttle);
        }
        else{
        	turningThrottleScale = 0.75;
        }
    	turningThrottleScale *= 1.5;
    	
    	right += turn * turningThrottleScale;  
        left -= turn * turningThrottleScale;
        
        if(Math.abs(right) <= 0.05)
        	right = 0;
        if(Math.abs(left) <= 0.05)
        	left = 0;
        
        if(left >= leftLastSpeed + rate)
        	leftSpeed = leftLastSpeed + rate;
        else if (left <= leftLastSpeed - rate)
        	leftSpeed = leftLastSpeed - rate;
        else
        	leftSpeed = left;
        
        if(right >= rightLastSpeed + rate)
        	rightSpeed = rightLastSpeed + rate;
        else if (right <= rightLastSpeed - rate)
        	rightSpeed = rightLastSpeed - rate;
        else
        	rightSpeed = right;
        
        setMotorValues(leftSpeed, rightSpeed);
        
        leftLastSpeed = leftSpeed;
        rightLastSpeed = rightSpeed;
    }
    
    public void setMotorValues(double left, double right){
    	leftMotorOne.set(left);
    	leftMotorTwo.set(-left);
    	rightMotorOne.set(right);
    	rightMotorTwo.set(-right);
    }
    public void tankDrive(double left, double right){
    	setMotorValues(left, right);
    }
    
    public void PIDDriveEncoder(){
    	double rightSpeed = RightDriveEncoderPID.calculate(rightEncoder.get());
    	double leftSpeed = LeftDriveEncoderPID.calculate(leftEncoder.get());
    	double gyroOutput = GyroPID.calculate(spartanGyro.getAngle());
    	rightSpeed -= gyroOutput;
    	leftSpeed += gyroOutput;
    	rightSpeed *= 0.5;
    	leftSpeed *= 0.5;
    	if(leftSpeed > 0.5){
    		leftSpeed = 0.5;
    	}
    	if(rightSpeed > 0.5){
    		rightSpeed = 0.5;
    	}    	if(leftSpeed < -0.5){
    		leftSpeed = -0.5;
    	}
    	if(rightSpeed < -0.5){
    		rightSpeed = -0.5;
    	}
    	
    	SmartDashboard.putNumber("Right PID Output", rightSpeed);
    	SmartDashboard.putNumber("Left PID Output", -leftSpeed);
    	setMotorValues(-leftSpeed, rightSpeed);
    }
    
    public void motionProfile(){
    	ChassisMP.calculate(rightEncoder.get(), leftEncoder.get());
    	double gyroOutput = GyroPID.calculate(spartanGyro.getAngle());
    	double rightSpeed = ChassisMP.getRightOutput();
    	double leftSpeed = ChassisMP.getLeftOutput();
    	rightSpeed -= gyroOutput;
    	leftSpeed += gyroOutput;
    	SmartDashboard.putNumber("MP output", rightSpeed);
    	setMotorValues(-leftSpeed, -rightSpeed);
    }
    
    public void splineProfile(){
    	chassisSP.calculate(leftEncoder.get(), rightEncoder.get());
    	double leftSpeed = chassisSP.getLeftOutput() * 0.5;
    	double rightSpeed = chassisSP.getRightOutput() * 0.5;
    	SmartDashboard.putNumber("spline speed", -leftSpeed);
    	setMotorValues(-leftSpeed, -rightSpeed);
    }
    
    public void calculate(){
    	double rightEncSpeed = rightEncoder.getRate();
    	double rightSpeed = rightEncSpeed - lastRightSpeed;
    	lastRightSpeed = rightEncSpeed;
    	
    	double leftEncSpeed = leftEncoder.getRate();
    	double leftSpeed = leftEncSpeed - lastLeftSpeed;
    	lastLeftSpeed = leftEncSpeed;

    	double currentTime = Timer.getFPGATimestamp();
    	double time = currentTime - lastTime;
    	lastTime = currentTime;
    	
    	double rightAcc = rightSpeed / time;
    	double leftAcc = leftSpeed / time;

    	double avg = (rightAcc + leftAcc) / 2;
    	accel.add(avg);
    	
    	double speedAvg = (leftEncSpeed + rightEncSpeed) / 2;

    	SmartDashboard.putNumber("MP speed", speedAvg);
		  SmartDashboard.putNumber("Accel Array", avg);
    }
    
    public void gyroPID(){
    	double speed = GyroPID.calculate(spartanGyro.getAngle()) * 4;
    	double leftSpeed = -speed;
    	double rightSpeed = speed;
    	setMotorValues(leftSpeed, rightSpeed);
    }
	
    public ArrayList<Double> getAvgAcc(){
    	return accel;
    }
    
}
