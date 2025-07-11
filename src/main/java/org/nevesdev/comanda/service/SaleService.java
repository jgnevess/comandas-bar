package org.nevesdev.comanda.service;

import org.nevesdev.comanda.dto.SalePreview;
import org.nevesdev.comanda.model.order.order.Order;
import org.nevesdev.comanda.model.order.order.Status;
import org.nevesdev.comanda.model.sale.Sale;
import org.nevesdev.comanda.repository.SaleRepository;
import org.nevesdev.comanda.service.interfaces.SaleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements SaleServiceInterface {

    @Autowired
    private SaleRepository saleRepository;

    protected Sale saveSale(Order order) {
        Sale sale = new Sale(order);
        if(order.getStatus().equals(Status.OPEN)) return null;
        return saleRepository.save(sale);
    }

    @Override
    public Page<SalePreview> findAllSalesByOrderDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate, int page) {
        List<Sale> sales = saleRepository.findAllSalesByOrderOrderDateTimeBetween(startDate, endDate);
        List<SalePreview> previews = sales.stream().map(SalePreview::new).toList();
        int start = page * 10;
        int end = Math.min(start + 10, sales.size());
        return new PageImpl<>(
                previews.subList(start, end),
                PageRequest.of(page, 10, Sort.by("id")),
                previews.size()
        );
    }

    @Override
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id).orElse(null);
    }
}
