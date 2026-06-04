package com.calorie.calculator.payload.request;

import com.calorie.calculator.model.User.ActivityLevel;
import com.calorie.calculator.model.User.Gender;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {
    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 120)
    private String password;

    private String fullName;
    private Integer age;
    private Double weight;
    private Double height;
    private Gender gender;
    private ActivityLevel activityLevel;

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
        // Add setters for tests and mapping
        public void setUsername(String username) { this.username = username; }
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public void setAge(Integer age) { this.age = age; }
        public void setWeight(Double weight) { this.weight = weight; }
        public void setHeight(Double height) { this.height = height; }
        public void setGender(Gender gender) { this.gender = gender; }
        public void setActivityLevel(ActivityLevel activityLevel) { this.activityLevel = activityLevel; }
    public String getFullName() { return fullName; }
    public Integer getAge() { return age; }
    public Double getWeight() { return weight; }
    public Double getHeight() { return height; }
    public Gender getGender() { return gender; }
    public ActivityLevel getActivityLevel() { return activityLevel; }
}
