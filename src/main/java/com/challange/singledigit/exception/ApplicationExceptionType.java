package com.challange.singledigit.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ApplicationExceptionType {

    ENTITY_NOT_FOUND("Entidade não encontrada.", NOT_FOUND),
    EMAIL_ALREADY_USED("Email já cadastrado.", CONFLICT),
    INVALID_RSA_KEY("Chave RSA inválida.", BAD_REQUEST),
    TOO_LONG_DATA_TO_BE_ENCRYPTED("Dado muito longo ou que já foi criptografado.", BAD_REQUEST),
    TOO_LONG_DATA_TO_BE_DECRYPTED("Informação muito longa para ser descriptografada pela chave.", BAD_REQUEST),
    USER_DATA_ALREADY_ENCRYPTED("Os dados do usuário já foram criptografados.", FORBIDDEN),
    DECRYPTION_ERROR("Provavelmente seu dado já está descriptografado.", BAD_REQUEST),
    INTERNAL_ERROR("Erro interno da aplicação.", INTERNAL_SERVER_ERROR),
    INVALID_DIGIT("O valor digitado não é um número válido" , BAD_REQUEST);

    private final String message;
    private final HttpStatus returnStatus;
}
