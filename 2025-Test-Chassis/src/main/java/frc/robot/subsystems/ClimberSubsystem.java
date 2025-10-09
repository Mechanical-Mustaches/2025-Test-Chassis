package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import java.util.ArrayList;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private SparkMax climbMotor = new SparkMax(19, MotorType.kBrushless);

    private final static double DETECTION_THRESHOLD = 15;
    private static final long MEASUREMENT_WINDOW = 200;
    // this number represents the minimum number of recent measurements needed to
    // calculate average amperage
    private static final int RECENT_MEASUREMENT_COUNT = 10;

    private ArrayList<AmperageMeasurements> amperageMeasurements = new ArrayList<AmperageMeasurements>();

    private record AmperageMeasurements(long time, double amperage) {
        public boolean isRecent(long milliseconds) {
            return System.currentTimeMillis() - time <= milliseconds;
        }
    }

    private AmperageMeasurements getCurrentMeasurement() {
        return new AmperageMeasurements(System.currentTimeMillis(), climbMotor.getOutputCurrent());
    }

    public boolean isRightDirection() {
        return (getAverageAmperage() >= DETECTION_THRESHOLD);
    }

    private double getAverageAmperage() {
        if (amperageMeasurements.isEmpty()) {
            return -1;
        }

        var recentMeasurementCount = 0;
        double sumOfElements = 0;

        for (var measurement : amperageMeasurements) {
            sumOfElements = sumOfElements + measurement.amperage;
            if (measurement.isRecent(MEASUREMENT_WINDOW)) {
                recentMeasurementCount = recentMeasurementCount + 1;
            }
        }

        if (recentMeasurementCount < RECENT_MEASUREMENT_COUNT) {
            return -1;
        }

        return sumOfElements / amperageMeasurements.size();
    }

    public boolean hasEnoughMeasurements() {
        return (getAverageAmperage() != 0);
    }

    private void removeMeasurements() {
        amperageMeasurements.removeIf(measurement -> !measurement.isRecent(MEASUREMENT_WINDOW));
    }

    public void climb() {
        climbMotor.set(0.5);
    }

    public void unclimb() {
        climbMotor.set(-0.5);
    }

    public void stopClimb() {
        climbMotor.set(0);
    }

    @Override
    public void periodic() {
        removeMeasurements();
        amperageMeasurements.add(getCurrentMeasurement());
    }
}