package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception;

import lombok.Getter;

@Getter
public class KeycloakFetchException extends RuntimeException{
    private int errorCode;
    private String errorMessage;
    public KeycloakFetchException(int errorCode, String errorMessage)
    {
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }
}
