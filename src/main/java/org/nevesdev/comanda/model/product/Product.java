package org.nevesdev.comanda.model.product;

import jakarta.persistence.*;
import lombok.*;
import org.nevesdev.comanda.dto.product.ProductCreate;
import org.nevesdev.comanda.dto.product.ProductUpdate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    @Column(nullable = false, unique = true)
    private String description;
    private Double costPrice;
    private Double sellingPrice;
    private Double offPrice;
    private Boolean isActive;
    private Category category;

    public Product(ProductCreate productCreate, String code) {
        this.description = productCreate.getDescription();
        this.costPrice = productCreate.getCostPrice();
        this.sellingPrice = productCreate.getSellingPrice();
        this.offPrice = productCreate.getOffPrice();
        this.category = productCreate.getCategory();
        this.isActive = false;
        this.code = code;
    }

    public void updateProduct(ProductUpdate productUpdate) {
        this.description = productUpdate.getDescription();
        this.costPrice = productUpdate.getCostPrice();
        this.sellingPrice = productUpdate.getSellingPrice();
        this.offPrice = productUpdate.getOffPrice();
        this.category = productUpdate.getCategory();
        this.isActive = productUpdate.getIsActive();
    }
}
