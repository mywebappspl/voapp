package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    private String exceptions;
    public BadRequestException(String exceptions)
    {
        this.exceptions=exceptions;
    }
}
