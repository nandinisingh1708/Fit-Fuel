package com.calorie.calculator.controller;

import com.calorie.calculator.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * REST Controller for Statistics and Analytics
 * Provides comprehensive health and fitness analytics
 */
@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class StatsController {
    
    private final StatsService statsService;

    /**
     * Get comprehensive personal statistics
     * @return Overall stats (total meals, average calories, streaks, goals)
     */
    @GetMapping("/personal")
    public ResponseEntity<Map<String, Object>> getPersonalStats() {
        Map<String, Object> stats = statsService.getPersonalStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get monthly calorie trends
     * @param month Month number (1-12)
     * @param year Year
     * @return Daily calorie data for the month
     */
    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyStats(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        Map<String, Object> stats = statsService.getMonthlyStats(month, year);
        return ResponseEntity.ok(stats);
    }

    /**
     * Get weekly comparison statistics
     * @return Current week vs previous week comparison
     */
    @GetMapping("/weekly-comparison")
    public ResponseEntity<Map<String, Object>> getWeeklyComparison() {
        Map<String, Object> comparison = statsService.getWeeklyComparison();
        return ResponseEntity.ok(comparison);
    }

    /**
     * Get goal progress tracking
     * @return Current progress towards all user goals
     */
    @GetMapping("/goal-progress")
    public ResponseEntity<Map<String, Object>> getGoalProgress() {
        Map<String, Object> progress = statsService.getGoalProgress();
        return ResponseEntity.ok(progress);
    }

    /**
     * Get nutrition macros breakdown
     * @param startDate Start date for analysis
     * @param endDate End date for analysis
     * @return Protein, carbs, fat distribution
     */
    @GetMapping("/macros")
    public ResponseEntity<Map<String, Object>> getMacrosBreakdown(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        Map<String, Object> macros = statsService.getMacrosBreakdown(startDate, endDate);
        return ResponseEntity.ok(macros);
    }

    /**
     * Get calorie deficit/surplus analysis
     * @return Daily calorie balance relative to goals
     */
    @GetMapping("/calorie-balance")
    public ResponseEntity<Map<String, Object>> getCalorieBalance() {
        Map<String, Object> balance = statsService.getCalorieBalance();
        return ResponseEntity.ok(balance);
    }

    /**
     * Get consistency metrics
     * @return Streak information, logging consistency, goal achievement rate
     */
    @GetMapping("/consistency")
    public ResponseEntity<Map<String, Object>> getConsistencyMetrics() {
        Map<String, Object> metrics = statsService.getConsistencyMetrics();
        return ResponseEntity.ok(metrics);
    }

    /**
     * Get health score
     * @return Overall health score based on goals achievement and consistency
     */
    @GetMapping("/health-score")
    public ResponseEntity<Map<String, Object>> getHealthScore() {
        Map<String, Object> score = statsService.calculateHealthScore();
        return ResponseEntity.ok(score);
    }

    /**
     * Get meal patterns and insights
     * @return Most eaten foods, meal timing patterns, favorite meals
     */
    @GetMapping("/meal-insights")
    public ResponseEntity<Map<String, Object>> getMealInsights() {
        Map<String, Object> insights = statsService.getMealInsights();
        return ResponseEntity.ok(insights);
    }
}
