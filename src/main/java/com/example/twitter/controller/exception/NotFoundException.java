package com.example.twitter.controller.exception;

import lombok.NonNull;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(@NonNull final String message) {
        super(message);
    }

}
