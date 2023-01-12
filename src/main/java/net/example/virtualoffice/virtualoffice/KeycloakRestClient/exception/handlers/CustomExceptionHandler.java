package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.handlers;

import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.strefaphp.virtualoffice.virtualoffice.KeycloakRestClient.exception.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMsg> BadRequestHandleException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg("Unknown error."));
    }
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorMsg> BadRequestHandleException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(e.getExceptions()));
    }
    @ExceptionHandler(value = FetchExceptions.class)
    public ResponseEntity<ErrorMsg> FetchHandleException(FetchExceptions e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg(e.getExceptions()));
    }
    @ExceptionHandler(value = KeycloakFetchException.class)
    public ResponseEntity<String> KeycloakFetchHandleException(KeycloakFetchException e)
    {
        return ResponseEntity.status(e.getErrorCode()).contentType(MediaType.APPLICATION_JSON).body(e.getErrorMessage());
    }
    @ExceptionHandler(value = ServerExceptions.class)
    public ResponseEntity<ErrorMsg> ServerHandleException(FetchExceptions e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMsg(e.getExceptions()));
    }
}