package org.nevesdev.comanda.config.exceptions;

import org.nevesdev.comanda.dto.error.ExceptionInfo;
import org.nevesdev.comanda.exceptions.OrderException;
import org.nevesdev.comanda.exceptions.ProductNotFoundException;
import org.nevesdev.comanda.exceptions.SaleException;
import org.nevesdev.comanda.exceptions.UsernameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameException.class)
    private ResponseEntity<ExceptionInfo> usernameAlreadyExists(UsernameException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ExceptionInfo(exception.getStatus(),
                exception.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<ExceptionInfo> productNotFound(ProductNotFoundException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ExceptionInfo(exception.getStatus(),
                exception.getMessage()));
    }

    @ExceptionHandler(OrderException.class)
    private ResponseEntity<ExceptionInfo> orderException(OrderException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ExceptionInfo(exception.getStatus(),
                exception.getMessage()));
    }

    @ExceptionHandler(SaleException.class)
    private ResponseEntity<ExceptionInfo> saleException(SaleException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ExceptionInfo(exception.getStatus(),
                exception.getMessage()));
    }
}
