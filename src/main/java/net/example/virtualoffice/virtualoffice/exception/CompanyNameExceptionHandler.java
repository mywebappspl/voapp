package net.example.virtualoffice.virtualoffice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyNameExceptionHandler extends RuntimeException{
    private String exception;
    public CompanyNameExceptionHandler()
    {
        this.exception = "Company name already exist";
    }

}
