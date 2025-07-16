package org.nevesdev.comanda.service.interfaces;

import org.nevesdev.comanda.dto.product.ProductCreate;
import org.nevesdev.comanda.dto.product.ProductCreated;
import org.nevesdev.comanda.dto.product.ProductUpdate;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;


public interface ProductServiceInterface {

    ProductCreated createProduct(ProductCreate productCreate);
    Page<ProductCreated> getAllProducts(int page, int pageSize);
    ProductCreated getById(Long id);
    ProductCreated updateProduct(Long id, ProductUpdate productUpdate);
    ProductCreated fastActiveProduct(Long id);
    ProductCreated addProduct(Long id, Integer quantity);
    ProductCreated removeProduct(Long id, Integer quantity);
    Page<ProductCreated> getAllActive(int page, int pageSize);
    Page<ProductCreated> getAllInactive(int page, int pageSize);
}
