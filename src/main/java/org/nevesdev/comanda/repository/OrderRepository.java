package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.order.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
