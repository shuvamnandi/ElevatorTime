# Elevator Time

## Concepts used in Solving this Problem

## Assumptions
1. The elevator at rest when it starts (hence initial velocity is used as 0 in the below equations)
2. The elevator arrives at complete rest when it comes to a stop
3. Deceleration typically is negative (in contrast to acceleration), but for this problem's solution it's treated similarly as acceleration. It has no impact on calculations since initial velocity is swapped with final velocity in these cases also, so the signs are still correct in calculations.
4. The wait time for opening/closing of doors is not considered in the travel time it will take a user to reach their destination after they enter the elevator.

# Kinematics equations of motion with constant acceleration 
# 1. Final Velocity (v) = Initial Velocity (u) + Acceleration (a) x Time (t)
$v = u + at$
- When Initial velocity of object is 0, $v = at$

# 2. Distance (s) = Initial Velocity x Time + 0.5 Acceleration x Time x Time
$s = ut + 0.5at^2$
- When Initial velocity of object is 0, $s = 0.5at^2$
- This is used in ```getTimeTakenToCoverDistanceBasedOnAcceleration``` to derive the time taken to cover a distance, given the acceleration

# 3. Final Velocity squared (v^2) = Initial Velocity squared (u^2) + 2 x Acceleration (a) x Distance (s)
$v^2 = u^2 + 2as$
- When Initial velocity of object is 0, $v^2 = 2as$
- This is used in ```getDistanceBasedOnMaxVelocityAndAcceleration``` to derive the distance the elevator can travel, given the maximum velocity it reaches and the rate of acceleration

# Logic used in ```Solution.class``` to find the time taken by elevator to travel between 2 floors
## 1. Scenario where elevator reaches the maximum speed
- The elevator starts off at the current floor at zero as the initial velocity. 
- It accelerates and covers some distance while it reaches its maximum velocity.
- After some amount of time, the lift starts to decelerate to reach its target floor at zero as the final velocity.
- Hence, Total time taken = Time spend while accelerating from 0 to Max Velocity + Time spend at maximum velocity + Time spend while decelerating from Max Velocity to 0
## 2. Scenario where elevator does not reach the maximum speed as the distance between 2 floors is too short
- The elevator starts off at the current floor at zero as the initial velocity.
- It accelerates and covers some distance before it needs to start decelerating again to reach the destination floor at zero as the final velocity.  
- We can consider the fact that the final velocity it reaches after accelerating (lets call it $v$) is the same velocity at which it will start to decelerate.
- Lets say that rate of acceleration is $a$, and rate of deceleration is $d$. The lift covers distance $s_a$ while accelerating and $s_d$ while decelerating 
- We can equate $v^2 = 2as_a$ with $v^2 = 2ds_d$ and then find out the correlation between $s_a$ and $s_d$ as $s_d=as_a/d$
- Therefore, total distance covered by the elevator $s = s_a + s_d$ => $s = s_a + as_a/d$
- And, velocity is $v = \sqrt{2as_a}$ which is also the same as $v = \sqrt{2ds_d}$ 
- Since we already know the total distance to be the distance between the 2 floors, we can solve for $s_a$ and find that the distance covered while accelerating, $s_a$ is $s_a = s / ( 1 + a/d)$
- This is used in ```getDistanceCoveredBasedOnSameVelocityReached``` to derive the distance covered while accelerating
- Once we find out $s_a$, it is straightforward to find $s_d$, which is $s_d = s - s_a$ 
- We can thereafter use the same logic as ```getTimeTakenToCoverDistanceBasedOnAcceleration``` to derive the time taken to cover both $s_a$ and $s_d$ respectively