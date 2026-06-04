package com.calorie.calculator.repository;

import com.calorie.calculator.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for Workout entity
 * Provides database operations for workout data
 */
@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    
    /**
     * Find all workouts for authenticated user ordered by date (newest first)
     */
    List<Workout> findAllByOrderByDateDesc();
    
    /**
     * Find workouts for a specific date
     */
    List<Workout> findByDate(LocalDate date);
    
    /**
     * Find workouts within a date range
     */
    List<Workout> findByDateBetweenOrderByDateDesc(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find workouts by activity type
     */
    List<Workout> findByName(String name);
    
    /**
     * Count workouts in a date range
     */
    long countByDateBetween(LocalDate startDate, LocalDate endDate);
}
