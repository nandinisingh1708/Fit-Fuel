package com.calorie.calculator.controller;

import com.calorie.calculator.model.Workout;
import com.calorie.calculator.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Workout Management
 * Handles workout logging, tracking, and calorie burn calculations
 */
@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class WorkoutController {
    
    private final WorkoutService workoutService;

    /**
     * Log a new workout
     * @param workout Workout details (name, duration, intensity, date)
     * @return Created workout with calculated calorie burn
     */
    @PostMapping
    public ResponseEntity<Workout> logWorkout(@Valid @RequestBody Workout workout) {
        Workout savedWorkout = workoutService.saveWorkout(workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkout);
    }

    /**
     * Get all workouts for authenticated user
     * @param date Optional filter by date
     * @return List of workouts
     */
    @GetMapping
    public ResponseEntity<List<Workout>> getUserWorkouts(
            @RequestParam(required = false) LocalDate date) {
        List<Workout> workouts = date != null 
            ? workoutService.getWorkoutsByDate(date)
            : workoutService.getAllUserWorkouts();
        return ResponseEntity.ok(workouts);
    }

    /**
     * Get daily workout summary
     * @param date Target date
     * @return Daily workout stats (total calories burned, exercises, duration)
     */
    @GetMapping("/daily-summary")
    public ResponseEntity<Map<String, Object>> getDailyWorkoutSummary(
            @RequestParam LocalDate date) {
        Map<String, Object> summary = workoutService.getDailyWorkoutSummary(date);
        return ResponseEntity.ok(summary);
    }

    /**
     * Get weekly workout statistics
     * @return Weekly stats including total calories, average duration, most active day
     */
    @GetMapping("/weekly-stats")
    public ResponseEntity<Map<String, Object>> getWeeklyStats() {
        Map<String, Object> stats = workoutService.getWeeklyWorkoutStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Update workout details
     * @param id Workout ID
     * @param workout Updated workout data
     * @return Updated workout
     */
    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(
            @PathVariable Long id,
            @Valid @RequestBody Workout workout) {
        Workout updated = workoutService.updateWorkout(id, workout);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete workout
     * @param id Workout ID
     * @return No content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get calorie burn estimate for a specific activity
     * @param activity Activity name (running, swimming, cycling, etc.)
     * @param duration Duration in minutes
     * @return Estimated calories burned
     */
    @GetMapping("/calorie-estimate")
    public ResponseEntity<Map<String, Double>> getCalorieEstimate(
            @RequestParam String activity,
            @RequestParam Integer duration) {
        Double caloriesBurned = workoutService.estimateCaloriesBurned(activity, duration);
        return ResponseEntity.ok(Map.of("activity", activity, "duration", (double) duration, "caloriesBurned", caloriesBurned));
    }
}
