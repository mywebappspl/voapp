package net.example.virtualoffice.virtualoffice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomExceptionHandler extends RuntimeException{
    private String exception;
    private HttpStatus status;
    public CustomExceptionHandler(ExceptionMessages exceptionMessages,HttpStatus status)
    {
        this.exception = exceptionMessages.getMessage();
        this.status=status;
    }
}
