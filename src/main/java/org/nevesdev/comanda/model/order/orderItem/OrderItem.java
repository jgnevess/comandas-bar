package org.nevesdev.comanda.model.order.orderItem;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.nevesdev.comanda.model.product.Product;

@Embeddable
@Data
public class OrderItem {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;

    public OrderItem(){}

    public OrderItem(Product product) {
        this.productId = product.getId();
        this.productName = product.getDescription();
        this.quantity = 0;
        this.unitPrice = product.getSellingPrice();
    }
}
