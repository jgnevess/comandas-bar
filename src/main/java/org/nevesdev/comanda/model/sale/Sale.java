package org.nevesdev.comanda.model.sale;

import jakarta.persistence.*;
import lombok.*;
import org.nevesdev.comanda.model.order.order.Order;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    public Sale(Order order) {
        this.order = order;
    }
}
