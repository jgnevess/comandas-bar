package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
