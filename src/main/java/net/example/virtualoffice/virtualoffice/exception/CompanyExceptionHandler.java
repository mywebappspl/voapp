package net.example.virtualoffice.virtualoffice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyExceptionHandler extends RuntimeException{
    private String exception;
    public CompanyExceptionHandler()
    {
        this.exception = "Company ID not exist";
    }

}
