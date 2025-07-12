package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.order.order.Order;
import org.nevesdev.comanda.model.order.order.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAll(Pageable pageable);
    Page<Order> findByStatus(Status status, Pageable pageable);
}
