package org.nevesdev.comanda.service.interfaces;

import org.nevesdev.comanda.dto.sale.SalePreview;
import org.nevesdev.comanda.model.sale.Sale;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleServiceInterface {

    Page<SalePreview> findAllSalesByOrderDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate, int page, int pageSize);
    Double findAllSalesByOrderDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    Sale getSaleById(Long id);
    List<Sale> getAllSalesByOrderDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
