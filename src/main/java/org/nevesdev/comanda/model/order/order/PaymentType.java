package org.nevesdev.comanda.model.order.order;

public enum PaymentType {
    DINHEIRO("Dinheiro"),
    PIX("Pix"),
    CREDITO("Credito"),
    DEBITO("Debito");

    private final String paymentType;

    PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return this.paymentType;
    }
}
