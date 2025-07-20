package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.product.Product;
import org.nevesdev.comanda.model.storage.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {


    Optional<Storage> findByProduct(Product product);

//    @Query(nativeQuery = true, value = "SELECT s.id, s.movement_date, s.quantity, s.product_id FROM storage s WHERE s.product_id = :productId")
//    Optional<Storage> findByProductId(@Param("productId") Long productId);
}
