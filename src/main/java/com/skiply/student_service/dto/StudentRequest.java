package com.skiply.student_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentRequest(
        @NotNull(message = "Student Id is required")
        @Min(value = 1, message = "Student ID must be a positive number")
        Long studentId,

        @NotBlank(message = "Student Name is required")
        String studentName,

        @NotBlank(message = "Grade is required")
        String grade,

        @NotBlank(message = "Mobile Number is required")
        String mobileNumber,

        @NotBlank(message = "School Name is required")
        String schoolName
) {}
