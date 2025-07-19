package org.nevesdev.comanda.exceptions;

public class NotValidException extends CommonException {
    public NotValidException(String message, int status) {
        super(message, status);
    }
}
