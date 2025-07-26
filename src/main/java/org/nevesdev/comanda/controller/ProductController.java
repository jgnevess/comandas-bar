package org.nevesdev.comanda.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.nevesdev.comanda.dto.product.ProductCreate;
import org.nevesdev.comanda.dto.product.ProductCreated;
import org.nevesdev.comanda.dto.product.ProductSelect;
import org.nevesdev.comanda.dto.product.ProductUpdate;
import org.nevesdev.comanda.dto.error.ExceptionInfo;
import org.nevesdev.comanda.service.interfaces.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/product")
@SecurityRequirement(name = "bearer-key")
public class ProductController {

    @Autowired
    private ProductServiceInterface productServiceInterface;

    @PostMapping
    public ResponseEntity<ProductCreated> createProduct(@RequestBody @Valid ProductCreate productCreate) {
        return ResponseEntity.status(201).body(productServiceInterface.createProduct(productCreate));
    }

    @GetMapping
    public ResponseEntity<Page<ProductCreated>> getAllProducts(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok().body(productServiceInterface.getAllProducts(page, pageSize));
    }

    @GetMapping("/all-active")
    public ResponseEntity<List<ProductSelect>> getAllActiveProducts() {
        return ResponseEntity.status(200).body(productServiceInterface.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(productServiceInterface.getById(id));
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductSelect>> getAllByDescription(@RequestParam String description) {
        return ResponseEntity.status(200).body(productServiceInterface.getAllByDescription(description));
    }

    @PatchMapping("/add/{id}")
    public ResponseEntity<ProductCreated> addProduct(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.status(200).body(productServiceInterface.addProduct(id,  quantity));
    }
    @PatchMapping("/remove/{id}")
    public ResponseEntity<ProductCreated> removeProduct(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.status(200).body(productServiceInterface.removeProduct(id,  quantity));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ProductCreated> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductUpdate productUpdate) {
        return ResponseEntity.status(200).body(productServiceInterface.updateProduct(id, productUpdate));
    }

    @PatchMapping("fast-active/{id}")
    public ResponseEntity<ProductCreated> fastActiveProductById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(productServiceInterface.fastActiveProduct(id));
    }


    @GetMapping("active")
    public ResponseEntity<Page<ProductCreated>> getAllActiveProducts(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.status(200).body(productServiceInterface.getAllActive(page, pageSize));
    }

    @GetMapping("inactive")
    public ResponseEntity<Page<ProductCreated>> getAllInactiveProducts(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.status(200).body(productServiceInterface.getAllInactive(page, pageSize));
    }
}
