package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class SmartClimbCommand extends Command {
    ClimberSubsystem climberSubsystem;
    private boolean lock = false;

    public SmartClimbCommand(ClimberSubsystem climberSubsystem) {
        this.climberSubsystem = climberSubsystem;
    }

    @Override
    public void initialize() {
        climberSubsystem.climb();
    }

    @Override
    public void execute() {
        if (lock) {
            return;
        }

        if (!climberSubsystem.hasEnoughMeasurements()) {
            return;
        } else if (climberSubsystem.isRightDirection()) {
            lock = true;
        } else {
            climberSubsystem.unclimb();
            lock = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        climberSubsystem.stopClimb();
    }

}
