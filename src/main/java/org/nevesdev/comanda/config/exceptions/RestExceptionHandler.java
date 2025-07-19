package org.nevesdev.comanda.config.exceptions;

import org.nevesdev.comanda.dto.error.ExceptionInfo;
import org.nevesdev.comanda.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameException.class)
    public ResponseEntity<ExceptionInfo> usernameAlreadyExists(UsernameException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ExceptionInfo(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionInfo> productNotFound(ProductNotFoundException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ExceptionInfo(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ExceptionInfo> orderException(OrderException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ExceptionInfo(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(SaleException.class)
    public ResponseEntity<ExceptionInfo> saleException(SaleException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ExceptionInfo(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ExceptionInfo> notValidException(NotValidException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ExceptionInfo(exception.getStatus(), exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        String firstError = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ExceptionInfo err = new ExceptionInfo(400, firstError);
        return ResponseEntity.status(400).body(err);
    }
}
