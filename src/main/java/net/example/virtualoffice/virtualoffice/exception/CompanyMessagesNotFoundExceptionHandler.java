package net.example.virtualoffice.virtualoffice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyMessagesNotFoundExceptionHandler extends RuntimeException{
    private String exception;
    public CompanyMessagesNotFoundExceptionHandler()
    {
        this.exception = "Messages not found between requested dates";
    }

}
