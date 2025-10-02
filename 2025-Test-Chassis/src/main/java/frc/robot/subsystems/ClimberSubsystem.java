package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private SparkMax climbMotor = new SparkMax(19, MotorType.kBrushless);

    public void climb() {
        climbMotor.set(0.5);
    }

    public void unclimb() {
        climbMotor.set(-0.5);
    }

    public void stopClimb() {
        climbMotor.set(0);
    }
}