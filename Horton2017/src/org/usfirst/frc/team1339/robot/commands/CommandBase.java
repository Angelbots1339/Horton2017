package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1339.robot.OI;
import org.usfirst.frc.team1339.robot.subsystems.*;


/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {
    
    Command autonomousCommand;

    public static OI oi;
    public static Chassis chassis = new Chassis();
    public static Intake intake = new Intake();
    public static Shooter shooter = new Shooter();
    
    public static void init() {
        oi = new OI();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
        
    }
}