package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllSalesByOrderOrderDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end);
}
