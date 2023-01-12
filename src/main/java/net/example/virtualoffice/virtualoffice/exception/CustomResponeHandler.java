package net.example.virtualoffice.virtualoffice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomResponeHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomResponeHandler.class);
    @ExceptionHandler(value = ExportToCSVExceptionHandler.class)
    public ResponseEntity<ErrorMsg> ExportToCSVHandleException(ExportToCSVExceptionHandler e) {
        logger.error("CSV export: "+e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(e.getException()));
    }
    @ExceptionHandler(value = CompanyExceptionHandler.class)
    public ResponseEntity<ErrorMsg> CompanyException(CompanyExceptionHandler e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg("Company not found"));
    }
    @ExceptionHandler(value = CompanyNameExceptionHandler.class)
    public ResponseEntity<ErrorMsg> CompanyNameException(CompanyNameExceptionHandler e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMsg("Company name already exists"));
    }
    @ExceptionHandler(value = CompanyMessagesNotFoundExceptionHandler.class)
    public ResponseEntity<ErrorMsg> CompanyMessagesNotFoundException(CompanyMessagesNotFoundExceptionHandler e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg("Messages not found between requested dates"));
    }
    @ExceptionHandler(value = MemberExceptionHandler.class)
    public ResponseEntity<ErrorMsg> MemberNameException(MemberExceptionHandler e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMsg("Member name already exists for this company"));
    }
    @ExceptionHandler(value = MemberNameNotExceptionHandler.class)
    public ResponseEntity<ErrorMsg> MemberNameNotExistException(MemberNameNotExceptionHandler e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg("Member name not exists for this company"));
    }
    @ExceptionHandler(value = MembersNotInCompanyExceptionHandler.class)
    public ResponseEntity<ErrorMsg> MembersNotInCompanyExceptionHandler(MembersNotInCompanyExceptionHandler e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg("Active member not assigned to company"));
    }
    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidHandleException(MethodArgumentNotValidException ex) {
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error-> {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        logger.error("Argument exception: "+errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        //logger.error("Argument exception: "+ex.getBindingResult().getFieldError().getDefaultMessage());
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(ex.getBindingResult().getFieldError().getDefaultMessage()));
    }
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        logger.error("System exception: "+ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMsg("Unidentified exception"));
    }
    @ExceptionHandler(value = LogsExceptionHandler.class)
    public ResponseEntity<ErrorMsg> LogsExceptionHandler(LogsExceptionHandler e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg("Incorrect dates in params"));
    }
    @ExceptionHandler(value = DatesExceptionHandler.class)
    public ResponseEntity<ErrorMsg> DatesExceptionHandler(DatesExceptionHandler e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg("Incorrect dates in params"));
    }

}
