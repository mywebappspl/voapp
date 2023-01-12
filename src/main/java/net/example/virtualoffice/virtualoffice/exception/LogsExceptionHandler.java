package net.example.virtualoffice.virtualoffice.exception;

public class LogsExceptionHandler  extends RuntimeException{
    private String exception;
    public LogsExceptionHandler(String customMsg)
    {
        this.exception = "Messages for Log exception: "+customMsg;
    }
}
