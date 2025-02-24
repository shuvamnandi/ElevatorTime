package com.shuvam;

import java.util.Map;
import static com.shuvam.KinematicsCalculator.*;

public class Solution {

    private static final Map<Integer, Double> FLOOR_HEIGHT_MAP = Map.of(0, 2.5, 1, 5.2,
            2, 8.5, 3, 11.5,
            4, 14.2, 5, 17.8);

    public static void main(String[] args) {
        // inputs, destination
        int floor1 = Integer.parseInt(args[0]);
        int floor2 = Integer.parseInt(args[1]);

        // max velocity in m/s, how much is maximum speed of the elevator
        double maxVelocity = Double.parseDouble(args[2]);

        // acceleration rate in m/s sq., how much the speed increases per unit seconds
        double accelerationRate = Double.parseDouble(args[3]);
        // deceleration rate in m/s sq., how much the speed decreases per unit seconds
        double decelerationRate = Double.parseDouble(args[4]);

        double distanceBetweenFloors = getDistanceBetweenFloors(floor1, floor2);

        System.out.println("Floor 1: " + floor1);
        System.out.println("Floor 2: " + floor2);
        System.out.println("MaxVelocity (m/s): " + maxVelocity);
        System.out.println("Acceleration (m/s/s): " + accelerationRate);
        System.out.println("Deceleration (m/s/s): " + decelerationRate);

        System.out.println("Distance to cover (m): " + distanceBetweenFloors);

        // Find the distance the elevator covers while trying to accelerate up to max velocity and decelerating from max velocity to 0.
        double distanceCoveredAccelerating = getDistanceCoveredBasedOnMaxVelocityAndAcceleration(maxVelocity, Math.abs(accelerationRate));
        double distanceCoveredDecelerating = getDistanceCoveredBasedOnMaxVelocityAndAcceleration(maxVelocity, Math.abs(decelerationRate));

        double totalTime;

        if ((distanceCoveredAccelerating + distanceCoveredDecelerating) >= distanceBetweenFloors) {
            double distanceCoveredWhileAcceleratingPartly = distanceBetweenFloors / ( 1 + accelerationRate / decelerationRate);
            double distanceCoveredWhileDeceleratingPartly = distanceBetweenFloors - distanceCoveredWhileAcceleratingPartly;

            double timeTakenInAccelerationMode = getTimeTakenToCoverDistanceBasedOnAcceleration(distanceCoveredWhileAcceleratingPartly, accelerationRate);
            double timeTakenInDecelerationMode = getTimeTakenToCoverDistanceBasedOnAcceleration(distanceCoveredWhileDeceleratingPartly, decelerationRate);
            totalTime = timeTakenInAccelerationMode + timeTakenInDecelerationMode;
        } else {
            // In this case, the elevator has enough distance to cover is able to reach the maximum velocity of the elevator
            // It will take fixed time to cover the distance while accelerating and decelerating
            double timeTakenFromZeroToReachPeakVelocity = getTimeTakenToReachPeakVelocity(maxVelocity, accelerationRate);
            double timeTakenFromPeakVelocityToReachZero = getTimeTakenToReachPeakVelocity(maxVelocity, decelerationRate);
            // It will cover the remaining distance in a fixed time while traveling at maxVelocity
            double remainingDistance = distanceBetweenFloors - (distanceCoveredAccelerating + distanceCoveredDecelerating);
            double timeTakenToCoverRemainingDistance = getTimeTakenToCoverDistanceFromVelocity(remainingDistance, maxVelocity);
            totalTime = timeTakenFromZeroToReachPeakVelocity + timeTakenToCoverRemainingDistance + timeTakenFromPeakVelocityToReachZero;
        }
        System.out.println("Total time taken to go from floor " + floor1 + " to " + floor2 + " = "  + totalTime + " seconds");
    }

    public static double getDistanceBetweenFloors(int floor1, int floor2) {
        Double heightFloor1 = FLOOR_HEIGHT_MAP.get(floor1);
        Double heightFloor2 = FLOOR_HEIGHT_MAP.get(floor2);

        assert heightFloor1 != null;
        assert heightFloor2 != null;

        return Math.abs(heightFloor2 - heightFloor1);
    }


}