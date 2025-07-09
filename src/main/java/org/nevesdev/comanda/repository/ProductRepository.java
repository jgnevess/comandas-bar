package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
