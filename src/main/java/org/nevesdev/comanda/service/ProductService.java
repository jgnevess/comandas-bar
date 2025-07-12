package org.nevesdev.comanda.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.nevesdev.comanda.dto.product.ProductCreate;
import org.nevesdev.comanda.dto.product.ProductCreated;
import org.nevesdev.comanda.dto.product.ProductQuantity;
import org.nevesdev.comanda.dto.product.ProductUpdate;
import org.nevesdev.comanda.exceptions.ProductNotFoundException;
import org.nevesdev.comanda.model.product.Product;
import org.nevesdev.comanda.model.storage.Storage;
import org.nevesdev.comanda.repository.ProductRepository;
import org.nevesdev.comanda.repository.StorageRepository;
import org.nevesdev.comanda.service.interfaces.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductService implements ProductServiceInterface {

    //region Dependency injection

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StorageRepository storageRepository;

    //endregion

    //region Implemented interface methods

    @Override
    public ProductCreated createProduct(ProductCreate productCreate) {
        String code = RandomStringUtils.randomNumeric(4, 6);
        Product product = new Product(productCreate, code);
        product = productRepository.save(product);
        return new ProductCreated(product, this.createStorage(product));
    }

    @Override
    public Page<ProductCreated> getAllProducts(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> {
            return new ProductCreated(product, this.getStorageQuantity(product));
        });
    }

    @Override
    public ProductCreated getById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) throw new ProductNotFoundException("Product not found", 404);
        int quantity = this.getStorageQuantity(product);
        if(quantity == -1) throw new ProductNotFoundException("Storage error", 500);
        return new ProductCreated(product, quantity);
    }

    @Override
    public ProductCreated updateProduct(Long id, ProductUpdate productUpdate) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) throw new ProductNotFoundException("Product not found", 404);
        product.updateProduct(productUpdate);
        product = productRepository.save(product);
        return new ProductCreated(product, this.getStorageQuantity(product));
    }

    @Override
    public ProductCreated fastActiveProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) throw new ProductNotFoundException("Product not found", 404);
        product.setIsActive(!product.getIsActive());
        product = productRepository.save(product);
        return new ProductCreated(product, this.getStorageQuantity(product));
    }

    @Override
    public ProductCreated addProduct(Long id, Integer quantity) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) throw new ProductNotFoundException("Product not found", 404);
        Storage storage = storageRepository.findByProduct(product).orElse(null);
        if(storage == null) throw new ProductNotFoundException("Storage error", 500);
        storage.setQuantity(storage.getQuantity() + quantity);
        storage = storageRepository.save(storage);
        return new ProductCreated(product, storage.getQuantity());
    }

    @Override
    public ProductCreated removeProduct(Long id, Integer quantity) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) throw new ProductNotFoundException("Product not found", 404);
        Storage storage = storageRepository.findByProduct(product).orElse(null);
        if(storage == null) throw new ProductNotFoundException("Storage error", 404);
        int value = storage.getQuantity() - quantity;
        if(value < 0) throw new ProductNotFoundException("Storage error", 404);
        storage.setQuantity(value);
        storage = storageRepository.save(storage);
        return new ProductCreated(product, storage.getQuantity());
    }

    @Override
    public Page<ProductCreated> getAllActive(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        Page<Product> products = productRepository.findByIsActiveTrue(pageable);
        return products.map(product -> {
            return new ProductCreated(product, this.getStorageQuantity(product));
        });
    }

    @Override
    public Page<ProductCreated> getAllInactive(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        Page<Product> products = productRepository.findByIsActiveFalse(pageable);
        return products.map(product -> {
            return new ProductCreated(product, this.getStorageQuantity(product));
        });
    }


    //endregion

    //region Private methods

    private Integer getStorageQuantity(Product product) {
        Storage storage = storageRepository.findByProduct(product).orElse(null);
        if(storage == null) return -1;
        return storage.getQuantity();
    }

    private Integer createStorage(Product product) {
        Storage storage = new Storage();
        storage.setProduct(product);
        storage.setMovementDate(LocalDateTime.now());
        storage.setQuantity(0);
        storage = storageRepository.save(storage);
        return storage.getQuantity();
    }

    //endregion

    protected ProductQuantity getProductById(Long id) {
        Product p = productRepository.findById(id).orElse(null);
        if(p == null) throw new ProductNotFoundException("Product not found", 404);
        return new ProductQuantity(p, this.getStorageQuantity(p));
    }
}
