package org.nevesdev.comanda.dto.product;

import lombok.Data;
import org.nevesdev.comanda.model.product.Product;

@Data
public class ProductSelect {
    private Long id;
    private String description;

    public ProductSelect(Product product) {
        this.id = product.getId();
        this.description = product.getDescription();
    }
}
