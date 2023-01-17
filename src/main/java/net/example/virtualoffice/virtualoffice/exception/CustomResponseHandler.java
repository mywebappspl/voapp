package net.example.virtualoffice.virtualoffice.exception;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomResponseHandler {
    @ExceptionHandler(value = CustomExceptionHandler.class)
    public ResponseEntity<ErrorMsg> CustomException(CustomExceptionHandler e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorMsg(e.getException()));
    }
}
