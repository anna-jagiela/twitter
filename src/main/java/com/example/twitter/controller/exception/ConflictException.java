package com.example.twitter.controller.exception;

import lombok.NonNull;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(value = CONFLICT)
public class ConflictException extends RuntimeException {

    public ConflictException(@NonNull final String message) {
        super(message);
    }

}
