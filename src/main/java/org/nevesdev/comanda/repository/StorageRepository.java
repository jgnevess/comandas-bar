package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.product.Product;
import org.nevesdev.comanda.model.storage.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findByProduct(Product product);
}
