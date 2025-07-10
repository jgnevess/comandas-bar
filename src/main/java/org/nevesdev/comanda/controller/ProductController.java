package org.nevesdev.comanda.controller;

import org.nevesdev.comanda.dto.product.ProductCreate;
import org.nevesdev.comanda.dto.product.ProductCreated;
import org.nevesdev.comanda.dto.product.ProductUpdate;
import org.nevesdev.comanda.error.ErrorInfo;
import org.nevesdev.comanda.service.interfaces.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/product")
public class ProductController {

    @Autowired
    private ProductServiceInterface productServiceInterface;

    @PostMapping
    public ResponseEntity<ProductCreated> createProduct(ProductCreate productCreate) {
        return ResponseEntity.status(201).body(productServiceInterface.createProduct(productCreate));
    }

    @GetMapping
    public ResponseEntity<Page<ProductCreated>> getAllProducts(@RequestParam int page) {
        return ResponseEntity.ok().body(productServiceInterface.getAllProducts(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        var response = productServiceInterface.getById(id);
        if(response == null) {
            return ResponseEntity.status(404).body(new ErrorInfo(404, "Produto não encontrado", "Verificar se o produto está correto"));
        }
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/add/{id}")
    public ResponseEntity<ProductCreated> addProduct(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.status(200).body(productServiceInterface.addProduct(id,  quantity));
    }
    @PatchMapping("/remove/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id, @RequestParam Integer quantity) {
        ProductCreated response = productServiceInterface.removeProduct(id,  quantity);
        if(response == null) {
            ErrorInfo e = new ErrorInfo(
                    400,
                    "Verificar a quantidade não pode ser menor que zero",
                    "Quantidade Menor que Zero");
            return ResponseEntity.status(400).body(e);
        }
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdate productUpdate
    ) {
        var response = productServiceInterface.updateProduct(id, productUpdate);
        if(response == null) {
            return ResponseEntity
                    .status(404)
                    .body(new ErrorInfo(404,
                            "Erro ao atualizar produto",
                            "Erro ao atualizar"));
        }
        return ResponseEntity.status(200).body(response);
    }
}
