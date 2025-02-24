package com.shuvam;

public class KinematicsCalculator {

    public static double getDistanceBasedOnMaxVelocityAndAcceleration(double maxVelocity, double acceleration) {
        /* Using the equation v^2 = u^2 + 2*a*s
        Given u (initial velocity) = 0, a (acceleration), solve for s (distance) as below
        */
        return Math.abs(Math.pow(maxVelocity, 2) / (2*acceleration));
    }

    public static double getTimeTakenToCoverDistanceBasedOnAcceleration(double distance, double acceleration) {
        /* Using the equation s = u*t + 1/2 a*t*t
        Given u (initial velocity) = 0, s (distance), a (acceleration), solve for t (time) as below
        */
        return Math.sqrt((2 * distance)/acceleration);
    }

    public static double getTimeTakenToReachPeakVelocity(double finalVelocity, double acceleration) {
        /* Using the equation v = u + a * t
        Given u (initial velocity) = 0, a (acceleration), t (time), solve for v (final velocity) as below
        */
        return finalVelocity / acceleration;
    }

    public static double getTimeTakenToCoverDistanceFromVelocity(double distance, double velocity) {
        /* Using the equation v = s / t
        Given s (distance), v (velocity), solve for t (time) as below
        */
        return distance / velocity;
    }
}
