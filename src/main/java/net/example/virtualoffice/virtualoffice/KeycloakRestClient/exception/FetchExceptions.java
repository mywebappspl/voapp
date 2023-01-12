package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception;

import lombok.Getter;

@Getter
public class FetchExceptions extends RuntimeException{
    private String exceptions;

    public FetchExceptions(String exceptions) {
        this.exceptions=exceptions;
    }
}
