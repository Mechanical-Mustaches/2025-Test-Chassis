package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SpinnyBoxSubsystem extends SubsystemBase {
    private SparkMax spinnyMotor = new SparkMax(9, MotorType.kBrushless);

    public void spin() {
        spinnyMotor.set(0.35);
    }

    public void spinOut() {
        spinnyMotor.set(-0.5);
    }

    public void stopSpin() {
        spinnyMotor.set(0);
    }
}
