package org.nevesdev.comanda.model.order.orderItem;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class OrderItem {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
}
