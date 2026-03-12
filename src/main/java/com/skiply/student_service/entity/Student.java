package com.skiply.student_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    private Long studentId;

    @NotBlank(message = "Student Name is mandatory")
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @NotBlank(message = "Grade is mandatory")
    @Column(name = "grade", nullable = false)
    private String grade;

    @NotBlank(message = "Mobile Number is mandatory")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Mobile number must be valid (10-15 digits)")
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @NotBlank(message = "School Name is mandatory")
    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

