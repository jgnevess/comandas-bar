package org.nevesdev.comanda.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.nevesdev.comanda.dto.sale.SalePreview;
import org.nevesdev.comanda.dto.error.ErrorInfo;
import org.nevesdev.comanda.service.interfaces.SaleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/sale")
@SecurityRequirement(name = "bearer-key")
public class SaleController {

    @Autowired
    private SaleServiceInterface saleServiceInterface;

    @GetMapping
    public ResponseEntity<Page<SalePreview>> getAllByOrderPeriod(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, int page) {
        return ResponseEntity.ok(saleServiceInterface.findAllSalesByOrderDateTimeBetween(startDate, endDate, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        var response = saleServiceInterface.getSaleById(id);
        return response == null ? ResponseEntity.status(400).body(
                new ErrorInfo(400, "Erro ao buscar venda", "Verifique o id da venda")
        ) : ResponseEntity.status(200).body(response);
    }
}
