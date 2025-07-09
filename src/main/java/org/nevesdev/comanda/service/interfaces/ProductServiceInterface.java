package org.nevesdev.comanda.service.interfaces;

import org.nevesdev.comanda.dto.product.ProductCreate;
import org.nevesdev.comanda.dto.product.ProductCreated;
import org.nevesdev.comanda.dto.product.ProductUpdate;

import java.util.List;

public interface ProductServiceInterface {

    ProductCreated createProduct(ProductCreate productCreate);
    List<ProductCreated> getAllProducts();
    ProductCreated getById(Long id);
    ProductCreated updateProduct(Long id, ProductUpdate productUpdate);
    ProductCreated fastActiveProduct(Long id);
}
