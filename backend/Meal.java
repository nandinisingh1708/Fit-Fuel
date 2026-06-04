package com.calorie.calculator.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal extends BaseEntity {
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private String name;
    
    @Column(nullable = false)
    private Double calories;
    
    @Column
    private String description;
    
    @Column
    private String category; // e.g., Breakfast, Lunch, Dinner, Snack
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private Integer quantity = 1;
    
    @Column
    private String imageUrl;

    // explicit getters/setters to avoid relying on Lombok when annotation processing fails
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
