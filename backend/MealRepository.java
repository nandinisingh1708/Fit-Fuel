package com.calorie.calculator.repository;

import com.calorie.calculator.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Meal> findByDate(LocalDate date);
    List<Meal> findByCategoryAndDate(String category, LocalDate date);
}
