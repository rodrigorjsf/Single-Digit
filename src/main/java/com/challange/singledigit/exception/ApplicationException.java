package com.challange.singledigit.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationException extends RuntimeException {
    private final ApplicationExceptionType type;

    public ApplicationException(ApplicationExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }

}
