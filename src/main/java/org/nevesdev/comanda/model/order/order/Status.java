package org.nevesdev.comanda.model.order.order;

public enum Status {
    OPEN("Aberto"),
    CLOSED("Fechado");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
