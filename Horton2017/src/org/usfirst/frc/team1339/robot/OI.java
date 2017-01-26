package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.robot.commandgroups.ChainingSpline;
import org.usfirst.frc.team1339.robot.commands.RunVision;
import org.usfirst.frc.team1339.robot.commands.SplineTest;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	//Joysticks
	private Joystick xboxStick = new Joystick(RobotMap.xboxPort);

	//Razer Joystick Buttons
	private JoystickButton AButton = new JoystickButton(xboxStick , RobotMap.xboxAbutton);
	private JoystickButton BButton = new JoystickButton(xboxStick , RobotMap.xboxBbutton);
	private JoystickButton XButton = new JoystickButton(xboxStick , RobotMap.xboxXbutton);
	private JoystickButton YButton = new JoystickButton(xboxStick , RobotMap.xboxYbutton);
	private JoystickButton leftBumper = new JoystickButton(xboxStick , RobotMap.xboxLeftBumper);
	private JoystickButton rightBumper = new JoystickButton(xboxStick , RobotMap.xboxRightBumper);
	private JoystickButton leftStickButton = new JoystickButton(xboxStick , RobotMap.xboxLeftStickButton);
	private JoystickButton rightStickButton = new JoystickButton(xboxStick , RobotMap.xboxRightStickButton);
	
	
	public OI(){
		 //XButton.whenPressed(new SplineTest(1500, 90, true));
		XButton.whenPressed(new ChainingSpline());
		AButton.whileHeld(new RunVision());
	}
	
	//Joystick get methods
	public Joystick getXboxStick(){
		return xboxStick;
	}
	public JoystickButton getAButton(){
		return AButton;
	}
	public JoystickButton getBButton(){
		return BButton;
	}
	public JoystickButton getXButton(){
		return XButton;
	}
	public JoystickButton getYButton(){
		return YButton;
	}
	public JoystickButton getLeftBumper(){
		return leftBumper;
	}
	public JoystickButton getRightBumper(){
		return rightBumper;
	}
	public JoystickButton getLeftStickButton(){
		return leftStickButton;
	}
	public JoystickButton getRightStickButton(){
		return rightStickButton;
	}
}
