package net.example.virtualoffice.virtualoffice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberNameNotExceptionHandler extends RuntimeException{
    private String exception;
    public MemberNameNotExceptionHandler()
    {
        this.exception = "Member not exist for this company";
    }
    public MemberNameNotExceptionHandler(String customMsg)
    {
        this.exception = "Member not exist: "+customMsg;
    }
}
