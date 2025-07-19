package org.nevesdev.comanda.model.order.order;

import jakarta.persistence.*;
import lombok.*;
import org.nevesdev.comanda.dto.order.OrderCreate;
import org.nevesdev.comanda.model.order.orderItem.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "order_tb")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientName;
    private LocalDateTime orderDateTime;
    @ElementCollection
    private List<OrderItem> items;
    private Double totalPrice;
    private PaymentType paymentType;
    private Status status;

    public Order(OrderCreate orderCreate) {
        this.clientName = orderCreate.getClientName();
        this.status = Status.OPEN;
        this.paymentType = null;
        this.orderDateTime = LocalDateTime.now();
        this.totalPrice = 0.0;
        this.items = new ArrayList<>();
    }

    public void setTotalPriceOrder() {
        this.totalPrice = 0.0;
        for(OrderItem item : items) {
            totalPrice += (item.getUnitPrice() * item.getQuantity());
        }
    }
}
