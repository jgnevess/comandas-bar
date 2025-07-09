package org.nevesdev.comanda.dto.product;

import lombok.Data;
import org.nevesdev.comanda.model.product.Category;

@Data
public class ProductCreate {
    private String description;
    private Double costPrice;
    private Double sellingPrice;
    private Double offPrice;
    private Category category;
}
