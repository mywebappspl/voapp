package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception;

import lombok.Getter;

@Getter
public class ErrorMsg {
    private String errorMessage;
    public ErrorMsg(String errorMessage)
    {
        this.errorMessage=errorMessage;
    }
}
