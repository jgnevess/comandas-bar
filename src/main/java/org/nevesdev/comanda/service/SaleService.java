package org.nevesdev.comanda.service;

import org.nevesdev.comanda.dto.sale.SalePreview;
import org.nevesdev.comanda.exceptions.SaleException;
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

import javax.security.sasl.SaslException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class SaleService implements SaleServiceInterface {

    @Autowired
    private SaleRepository saleRepository;

    protected Sale saveSale(Order order) {
        Sale sale = new Sale(order);
        if(order.getStatus().equals(Status.OPEN)) throw new SaleException("Order is open", 412);
        return saleRepository.save(sale);
    }

    @Override
    public Page<SalePreview> findAllSalesByOrderDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate, int page, int pageSize) {
        List<Sale> sales = saleRepository.findAllSalesByOrderOrderDateTimeBetween(startDate, endDate);
        sales.sort(Comparator.comparing((Sale s) -> s.getOrder().getOrderDateTime()));
        List<SalePreview> previews = sales.stream().map(SalePreview::new).toList();
        int start = page * pageSize;
        int end = Math.min(start + pageSize, sales.size());
        return new PageImpl<>(
                previews.subList(start, end),
                PageRequest.of(page, pageSize),
                previews.size()
        );
    }

    @Override
    public Double findAllSalesByOrderDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sale> sales = saleRepository.findAllSalesByOrderOrderDateTimeBetween(startDate, endDate);
        double response = 0.0;
        for(Sale sale : sales) {
            response += sale.getOrder().getTotalPrice();
        }
        return response;
    }

    @Override
    public Sale getSaleById(Long id) {
        Sale sale = saleRepository.findById(id).orElse(null);
        if(sale == null) throw new SaleException("Sale is not found", 404);
        return sale;
    }

    @Override
    public List<Sale> getAllSalesByOrderDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository
                .findAllSalesByOrderOrderDateTimeBetween(startDate, endDate)
                .stream().sorted((s1, s2) -> s1.getOrder().getOrderDateTime().compareTo(s2.getOrder().getOrderDateTime())).toList();
    }
}
