package org.nevesdev.comanda.exceptions;

public abstract class CommonException extends RuntimeException {

    private final int status;

    public CommonException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
