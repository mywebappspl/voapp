package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class KeycloakExceptionHandler extends RuntimeException{
    private String exception;
    public KeycloakExceptionHandler(ExceptionMessages exceptionMessages)
    {
        this.exception = exceptionMessages.getMessage();
    }
}
