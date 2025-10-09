// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ScoreCommand;
import frc.robot.commands.ScoreSequentialCommandGroup;
import frc.robot.commands.StopScoreCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.SpinnyBoxSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

import java.io.File;
import java.nio.file.FileSystem;
import edu.wpi.first.wpilibj.Filesystem;

import javax.security.auth.kerberos.DelegationPermission;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

    private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
    private final SpinnyBoxSubsystem spinnyBoxSubsystem = new SpinnyBoxSubsystem();
    private final SwerveDriveSubsystem swerveDriveSubsystem;

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController = new CommandXboxController(
            OperatorConstants.kDriverControllerPort);
    private final CommandGenericHID m_gunnerController = new CommandGenericHID(
            OperatorConstants.kGunnerControllerPort);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        swerveDriveSubsystem = new SwerveDriveSubsystem(
                new File(Filesystem.getDeployDirectory(), "swerve"));

        NamedCommands.registerCommand("Score", new ScoreSequentialCommandGroup(spinnyBoxSubsystem));

        // Configure the trigger bindings
        swerveDriveSubsystem.setDefaultCommand(swerveDriveSubsystem.driveCommand(
                () -> -MathUtil.applyDeadband(m_driverController.getRawAxis(1), 0.1),
                () -> -MathUtil.applyDeadband(m_driverController.getRawAxis(0), 0.1),
                () -> -MathUtil.applyDeadband(m_driverController.getRawAxis(4), 0.1)));

        configureBindings();

    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be
     * created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
     * an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
     * {@link
     * CommandXboxController
     * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or
     * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        new Trigger(m_exampleSubsystem::exampleCondition)
                .onTrue(new ExampleCommand(m_exampleSubsystem));

        // Schedule `exampleMethodCommand` when the Xbox controller's B button is
        // pressed,
        // cancelling on release.
        m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

        m_driverController.leftBumper().onTrue(
                new InstantCommand(() -> swerveDriveSubsystem.zeroGyro()));

        m_gunnerController.button(5).whileTrue(
                new InstantCommand(() -> climberSubsystem.climb()));
        m_gunnerController.button(5).onFalse(
                new InstantCommand(() -> climberSubsystem.stopClimb()));
        m_gunnerController.button(9).whileTrue(
                new InstantCommand(() -> climberSubsystem.unclimb()));
        m_gunnerController.button(9).onFalse(
                new InstantCommand(() -> climberSubsystem.stopClimb()));

        m_gunnerController.button(6).whileTrue(
                new InstantCommand(() -> spinnyBoxSubsystem.spin()));
        m_gunnerController.button(6).onFalse(
                new InstantCommand(() -> spinnyBoxSubsystem.stopSpin()));
        m_gunnerController.button(10).whileTrue(
                new InstantCommand(() -> spinnyBoxSubsystem.spinOut()));
        m_gunnerController.button(10).onFalse(
                new InstantCommand(() -> spinnyBoxSubsystem.stopSpin()));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return Autos.exampleAuto(m_exampleSubsystem);
    }
}
