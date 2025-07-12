package org.nevesdev.comanda.exceptions;

public class ProductNotFoundException extends CommonException {

    public ProductNotFoundException(String message, int status) {
        super(message, status);
    }
}
