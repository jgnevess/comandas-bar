package org.nevesdev.comanda.dto.product;

import lombok.Data;
import org.nevesdev.comanda.model.product.Product;

@Data
public class ProductQuantity {
    private Product product;
    private Integer quantity;

    public ProductQuantity(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
