package org.nevesdev.comanda.dto.product;

import lombok.Data;
import org.nevesdev.comanda.model.product.Category;
import org.nevesdev.comanda.model.product.Product;

@Data
public class ProductCreated {
    private Long id;
    private String description;
    private Double costPrice;
    private Double sellingPrice;
    private Double offPrice;
    private Category category;
    private Boolean isActive;
    private Integer quantity;
    private String code;

    public ProductCreated(Product product, Integer quantity) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.costPrice = product.getCostPrice();
        this.sellingPrice = product.getSellingPrice();
        this.offPrice = product.getOffPrice();
        this.category = product.getCategory();
        this.isActive = product.getIsActive();
        this.quantity = quantity;
        this.code = product.getCode();
    }

    public ProductCreated(){}
}

