package com.skiply.student_service.exception;

import java.io.Serial;

public class StudentAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentAlreadyExistsException(String message) {
        super(message);
    }
}
