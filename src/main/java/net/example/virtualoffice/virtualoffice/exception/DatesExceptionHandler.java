package net.example.virtualoffice.virtualoffice.exception;

public class DatesExceptionHandler extends RuntimeException{
    private String exception;
    public DatesExceptionHandler(String customMsg)
    {
        this.exception = "Messages for Date range exception: "+customMsg;
    }
}
