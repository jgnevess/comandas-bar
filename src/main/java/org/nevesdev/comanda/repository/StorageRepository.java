package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.storage.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
