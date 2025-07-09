package org.nevesdev.comanda.model.storage;

import jakarta.persistence.*;
import lombok.*;
import org.nevesdev.comanda.model.product.Product;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime movementDate;
    private Integer quantity;
    @OneToOne
    private Product product;
}
