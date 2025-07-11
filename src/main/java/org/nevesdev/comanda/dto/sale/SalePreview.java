package org.nevesdev.comanda.dto.sale;

import lombok.Data;
import org.nevesdev.comanda.model.order.order.PaymentType;
import org.nevesdev.comanda.model.sale.Sale;

import java.time.LocalDateTime;

@Data
public class SalePreview {

    private Long id;
    private LocalDateTime saleDate;
    private String clientName;
    private Double totalPrice;
    private PaymentType paymentType;

    public SalePreview(Sale sale) {
        this.id = sale.getId();
        this.saleDate = sale.getOrder().getOrderDateTime();
        this.clientName = sale.getOrder().getClientName();
        this.totalPrice = sale.getOrder().getTotalPrice();
        this.paymentType = sale.getOrder().getPaymentType();
    }
}
