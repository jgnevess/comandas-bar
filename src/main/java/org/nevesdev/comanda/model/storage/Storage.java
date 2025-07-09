package org.nevesdev.comanda.model.storage;

import jakarta.persistence.*;
import lombok.Data;
import org.nevesdev.comanda.model.product.Product;

import java.time.LocalDateTime;

@Entity
@Data
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime movementDate;
    private Integer quantity;
    @OneToOne
    private Product product;
}
