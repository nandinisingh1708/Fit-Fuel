package com.calorie.calculator.controller;

import com.calorie.calculator.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Personalized Recommendations
 * Provides meal and workout recommendations based on user profile and goals
 */
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class RecommendationController {
    
    private final RecommendationService recommendationService;

    /**
     * Get personalized meal recommendations
     * @param goal Fitness goal (weight_loss, maintenance, muscle_gain)
     * @param mealType Type of meal (breakfast, lunch, dinner, snack)
     * @return List of recommended meals with nutritional information
     */
    @GetMapping("/meals")
    public ResponseEntity<List<Map<String, Object>>> getMealRecommendations(
            @RequestParam(required = false) String goal,
            @RequestParam(required = false) String mealType) {
        List<Map<String, Object>> recommendations = recommendationService.getMealRecommendations(goal, mealType);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Get personalized workout recommendations
     * @param goal Fitness goal (weight_loss, maintenance, muscle_gain)
     * @return List of recommended workouts with details
     */
    @GetMapping("/workouts")
    public ResponseEntity<List<Map<String, Object>>> getWorkoutRecommendations(
            @RequestParam(required = false) String goal) {
        List<Map<String, Object>> recommendations = recommendationService.getWorkoutRecommendations(goal);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Get daily nutrition plan
     * @return Recommended meal plan for the day based on calorie goals
     */
    @GetMapping("/daily-plan")
    public ResponseEntity<Map<String, Object>> getDailyNutritionPlan() {
        Map<String, Object> plan = recommendationService.generateDailyNutritionPlan();
        return ResponseEntity.ok(plan);
    }

    /**
     * Get weekly meal plan
     * @return Suggested meal plan for the entire week
     */
    @GetMapping("/weekly-plan")
    public ResponseEntity<Map<String, Object>> getWeeklyMealPlan() {
        Map<String, Object> plan = recommendationService.generateWeeklyMealPlan();
        return ResponseEntity.ok(plan);
    }

    /**
     * Get workout plan for the week
     * @return Suggested workout schedule for the entire week
     */
    @GetMapping("/workout-schedule")
    public ResponseEntity<Map<String, Object>> getWeeklyWorkoutSchedule() {
        Map<String, Object> schedule = recommendationService.generateWeeklyWorkoutSchedule();
        return ResponseEntity.ok(schedule);
    }

    /**
     * Get quick meal alternatives for high-calorie foods
     * @param meal High-calorie meal to find alternatives for
     * @return List of healthier alternatives
     */
    @GetMapping("/meal-alternatives")
    public ResponseEntity<List<Map<String, Object>>> getMealAlternatives(
            @RequestParam String meal) {
        List<Map<String, Object>> alternatives = recommendationService.getMealAlternatives(meal);
        return ResponseEntity.ok(alternatives);
    }

    /**
     * Get personalized tips based on progress
     * @return Tips and suggestions based on current progress
     */
    @GetMapping("/tips")
    public ResponseEntity<List<String>> getPersonalizedTips() {
        List<String> tips = recommendationService.getPersonalizedTips();
        return ResponseEntity.ok(tips);
    }
}
