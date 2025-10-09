package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpinnyBoxSubsystem;

public class ScoreCommand extends Command {
    SpinnyBoxSubsystem spinnyBoxSubsystem;

    public ScoreCommand(SpinnyBoxSubsystem spinny) {
        spinnyBoxSubsystem = spinny;
    }

    @Override
    public void initialize() {
        spinnyBoxSubsystem.spin();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
