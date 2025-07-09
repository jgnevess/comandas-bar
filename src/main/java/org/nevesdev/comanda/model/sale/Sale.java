package org.nevesdev.comanda.model.sale;

import jakarta.persistence.*;
import lombok.Data;
import org.nevesdev.comanda.model.order.order.Order;

@Entity
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;
}
