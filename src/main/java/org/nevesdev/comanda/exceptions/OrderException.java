package org.nevesdev.comanda.exceptions;

public class OrderException extends CommonException {
    public OrderException(String message, int status) {
        super(message, status);
    }
}
