package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SpinnyBoxSubsystem;

public class ScoreSequentialCommandGroup extends SequentialCommandGroup {
    public ScoreSequentialCommandGroup(SpinnyBoxSubsystem spinnyBoxSubsystem) {
        super(
                new ScoreCommand(spinnyBoxSubsystem),
                new WaitCommand(0.5),
                new StopScoreCommand(spinnyBoxSubsystem));
    }
}
