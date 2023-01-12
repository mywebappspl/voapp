package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception;

import lombok.Getter;

@Getter
public class ServerExceptions extends RuntimeException{
    private String exceptions;

    public ServerExceptions(String exceptions) {
        this.exceptions=exceptions;
    }
}
