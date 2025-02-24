package com.shuvam;

import java.util.Map;
import static com.shuvam.KinematicsCalculator.*;

public class Solution {

    // Initialise Map with height level (meters) of each floor
    private static final Map<Integer, Double> FLOOR_HEIGHT_MAP = Map.of(0, 0.0, 1, 2.5,
            2, 5.2, 3, 8.7,
            4, 12.5, 5, 15.7, 6, 18.9, 17, 50.0);

    public static InputParameters getInputPrams(String[] args) {
        // inputs, destination
        int floor1 = Integer.parseInt(args[0]);
        int floor2 = Integer.parseInt(args[1]);
        // max velocity in m/s, how much is maximum speed of the elevator
        double maxVelocity = Double.parseDouble(args[2]);
        // acceleration rate in m/s sq., how much the speed increases per unit seconds
        double accelerationRate = Double.parseDouble(args[3]);
        // deceleration rate in m/s sq., how much the speed decreases per unit seconds
        double decelerationRate = Double.parseDouble(args[4]);

        System.out.println("INPUT PARAMETERS");
        System.out.println("Floor 1: " + floor1);
        System.out.println("Floor 2: " + floor2);
        System.out.println("MaxVelocity (m/s): " + maxVelocity);
        System.out.println("Acceleration (m/s/s): " + accelerationRate);
        System.out.println("Deceleration (m/s/s): " + decelerationRate);

        return new InputParameters(floor1, floor2, maxVelocity, accelerationRate, decelerationRate);
    }
    public static void main(String[] args) {
        InputParameters inputParameters = getInputPrams(args);
        double distanceBetweenFloors = getDistanceBetweenFloors(
                inputParameters.currentFloor(),
                inputParameters.targetFloor()
        );
        System.out.println("Distance to cover (m): " + distanceBetweenFloors);
        double totalTime = getElevatorTravelTimeBetweenTwoFloors(inputParameters, distanceBetweenFloors);
        System.out.println("Total time taken to go from floor " + inputParameters.currentFloor() + " to " + inputParameters.targetFloor() + " = "  + totalTime + " seconds");
    }

    public static double getElevatorTravelTimeBetweenTwoFloors(InputParameters inputParameters, double distance) {
        // Find the distance the elevator covers while trying to accelerate up to Max Velocity
        // and decelerating from Max Velocity to 0 when coming to a stop at its target floor
        double distanceCoveredAccelerating = getDistanceBasedOnMaxVelocityAndAcceleration(inputParameters.maxVelocity(), inputParameters.accelerationRate());
        double distanceCoveredDecelerating = getDistanceBasedOnMaxVelocityAndAcceleration(inputParameters.maxVelocity(), inputParameters.decelerationRate());

        // Based on whether there is enough distance between the 2 floors for elevator to reach Max Velocity,
        // we need to handle 2 different scenarios
        if ((distanceCoveredAccelerating + distanceCoveredDecelerating) >= distance) {
            // In this case, the elevator does not have enough distance to reach the maximum velocity of the elevator.
            // The elevator goes up from current floor accelerating for a certain amount of time,
            // but it won't reach its maximum velocity. It then slows down and covers the rest of the distance
            // while decelerating before coming to a full stop at the target floor.
            // Used mathematical equations to calculate the distance covered while increasing speed when accelerating
            // and slowing down when decelerating
            // See README.md for more details on calculating this formula
            double distanceCoveredWhileAcceleratingPartly = getDistanceCoveredBasedOnSameVelocityReached(distance, inputParameters.accelerationRate(), inputParameters.decelerationRate());
            double distanceCoveredWhileDeceleratingPartly = distance - distanceCoveredWhileAcceleratingPartly;
            double timeTakenInAccelerationMode = getTimeTakenToCoverDistanceBasedOnAcceleration(distanceCoveredWhileAcceleratingPartly, inputParameters.accelerationRate());
            double timeTakenInDecelerationMode = getTimeTakenToCoverDistanceBasedOnAcceleration(distanceCoveredWhileDeceleratingPartly, inputParameters.decelerationRate());
            return timeTakenInAccelerationMode + timeTakenInDecelerationMode;
        } else {
            // In this case, the elevator has enough distance to cover is able to reach the maximum velocity of the elevator
            // It will take a fixed time to cover the distance while accelerating and decelerating, respectively
            double timeTakenFromZeroToReachPeakVelocity = getTimeTakenToReachPeakVelocity(inputParameters.maxVelocity(), inputParameters.accelerationRate());
            double timeTakenFromPeakVelocityToReachZero = getTimeTakenToReachPeakVelocity(inputParameters.maxVelocity(), inputParameters.decelerationRate());
            // The elevator covers the remaining distance in a fixed time while traveling at maxVelocity
            double remainingDistance = distance - (distanceCoveredAccelerating + distanceCoveredDecelerating);
            // Total time taken is the sum of all these 3 time taken
            double timeTakenToCoverRemainingDistance = getTimeTakenToCoverDistanceFromVelocity(remainingDistance, inputParameters.maxVelocity());
            return timeTakenFromZeroToReachPeakVelocity + timeTakenToCoverRemainingDistance + timeTakenFromPeakVelocityToReachZero;
        }
    }

    public static double getDistanceBetweenFloors(int floor1, int floor2) {
        Double heightFloor1 = FLOOR_HEIGHT_MAP.get(floor1);
        Double heightFloor2 = FLOOR_HEIGHT_MAP.get(floor2);

        assert heightFloor1 != null;
        assert heightFloor2 != null;

        return Math.abs(heightFloor2 - heightFloor1);
    }


}