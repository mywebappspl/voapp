package net.example.virtualoffice.virtualoffice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersNotInCompanyExceptionHandler extends RuntimeException{
    private String exception;
    public MembersNotInCompanyExceptionHandler()
    {
        this.exception = "No active members assigned";
    }
    public MembersNotInCompanyExceptionHandler(String customMsg)
    {
        this.exception = "No active members assigned to: "+customMsg;
    }
}
