package com.calorie.calculator.model;

import lombok.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements UserDetails {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, unique = true)    
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(nullable = false, unique = true)  
    private String email;

    @NotBlank
    @Size(max = 120)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude // or @ToString at class level
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "weight")
    private Double weight; // in kg

    @Column(name = "height")
    private Double height; // in cm

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum ActivityLevel {
        SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE, EXTREMELY_ACTIVE
    }

    // Explicitly implement getUsername() & getPassword() so the class compiles even if Lombok
    // annotation processing is not enabled in the build environment or IDE.
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // Explicit getter for email to avoid Lombok-only dependency
    public String getEmail() {
        return email;
    }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setAge(Integer age) { this.age = age; }
    public void setWeight(Double weight) { this.weight = weight; }
    public void setHeight(Double height) { this.height = height; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setActivityLevel(ActivityLevel activityLevel) { this.activityLevel = activityLevel; }

    // Additional getters used by other parts
    public String getFullName() { return fullName; }
    public Integer getAge() { return age; }
    public Double getWeight() { return weight; }
    public Double getHeight() { return height; }
    public Gender getGender() { return gender; }
    public ActivityLevel getActivityLevel() { return activityLevel; }
}
