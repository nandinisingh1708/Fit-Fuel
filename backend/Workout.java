package com.calorie.calculator.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "workouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workout extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private Integer duration; // in minutes

    @NotNull
    private Double caloriesBurned;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WorkoutIntensity intensity;

    @NotNull
    private LocalDate workoutDate;

    @Column
    private LocalTime workoutTime;

    @Column
    private Double distance; // in kilometers, optional

    public enum WorkoutIntensity {
        LOW, MODERATE, HIGH
    }
}
