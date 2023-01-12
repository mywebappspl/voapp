package net.example.virtualoffice.virtualoffice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportToCSVExceptionHandler extends RuntimeException{
    private String exception;
    public ExportToCSVExceptionHandler()
    {
        this.exception = "Messages for company exception";
    }
    public ExportToCSVExceptionHandler(String customMsg)
    {
        this.exception = "Messages for company exception: "+customMsg;
    }
}
