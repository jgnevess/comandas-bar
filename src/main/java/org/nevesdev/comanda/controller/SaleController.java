package org.nevesdev.comanda.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.nevesdev.comanda.dto.sale.SalePreview;
import org.nevesdev.comanda.dto.error.ExceptionInfo;
import org.nevesdev.comanda.model.sale.Sale;
import org.nevesdev.comanda.service.interfaces.SaleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/sale")
@SecurityRequirement(name = "bearer-key")
public class SaleController {

    @Autowired
    private SaleServiceInterface saleServiceInterface;

    @GetMapping
    public ResponseEntity<Page<SalePreview>> getAllByOrderPeriod(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate,@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(saleServiceInterface.findAllSalesByOrderDateTimeBetween(startDate, endDate, page, pageSize));
    }

    @GetMapping("totalBetween")
    public ResponseEntity<Double> getAllByOrderPeriod(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(saleServiceInterface.findAllSalesByOrderDateTimeBetween(startDate, endDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(saleServiceInterface.getSaleById(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<Sale>> getAllSalesByOrderDateTimeBetween(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.status(200).body(saleServiceInterface.getAllSalesByOrderDateTimeBetween(startDate, endDate));
    }
}
