package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.bar.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarRepository extends JpaRepository<Bar, Long> {

    Boolean existsByBarName(String barName);
}
