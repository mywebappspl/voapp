package net.example.virtualoffice.virtualoffice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberExceptionHandler extends RuntimeException{
    private String exception;
    public MemberExceptionHandler()
    {
        this.exception = "Member already exists";
    }
    public MemberExceptionHandler(String customMsg)
    {
        this.exception = "Member already exists: "+customMsg;
    }
}
