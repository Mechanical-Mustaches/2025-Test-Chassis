package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpinnyBoxSubsystem;

public class StopScoreCommand extends Command {
    SpinnyBoxSubsystem spinnyBoxSubsystem;

    public StopScoreCommand(SpinnyBoxSubsystem spinny) {
        spinnyBoxSubsystem = spinny;
    }

    @Override
    public void initialize() {
        spinnyBoxSubsystem.stopSpin();
    }

    @Override
    public void end(boolean interrupted) {
        spinnyBoxSubsystem.stopSpin();
    }
}
