package org.nevesdev.comanda.dto.order;

import lombok.Data;
import org.nevesdev.comanda.model.order.order.Order;
import org.nevesdev.comanda.model.order.order.Status;

import java.time.LocalDateTime;

@Data
public class OrderPreview {
    private Long id;
    private String clientName;
    private LocalDateTime orderDateTime;
    private Double totalPrice;
    private Status status;

    public OrderPreview(Order order) {
        this.id = order.getId();
        this.clientName = order.getClientName();
        this.orderDateTime = order.getOrderDateTime();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
    }
}
