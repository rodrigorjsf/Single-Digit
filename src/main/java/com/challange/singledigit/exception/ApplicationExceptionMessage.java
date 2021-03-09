package com.challange.singledigit.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplicationExceptionMessage {
    @JsonProperty("mensagem")
    private final String message;
}

