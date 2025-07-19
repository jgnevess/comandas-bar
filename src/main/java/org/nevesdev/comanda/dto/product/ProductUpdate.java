package org.nevesdev.comanda.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.nevesdev.comanda.model.product.Category;

@Data
public class ProductUpdate {
    @NotBlank(message = "Descrição não pode ser em branco")
    @Size(min = 3, max = 50, message = "Descrição deve ter no de 3 a 50 caracteres")
    private String description;
    @Positive(message = "Preço de custo não pode ser nulo")
    private Double costPrice;
    @Positive(message = "Preço de venda não pode ser nulo")
    private Double sellingPrice;
    @Positive(message = "Preço de oferta não pode ser nulo")
    private Double offPrice;
    private Category category;
    private Boolean isActive;
}
