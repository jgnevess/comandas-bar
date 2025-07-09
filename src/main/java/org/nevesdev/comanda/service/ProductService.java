package org.nevesdev.comanda.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.nevesdev.comanda.dto.product.ProductCreate;
import org.nevesdev.comanda.dto.product.ProductCreated;
import org.nevesdev.comanda.dto.product.ProductUpdate;
import org.nevesdev.comanda.model.product.Product;
import org.nevesdev.comanda.model.storage.Storage;
import org.nevesdev.comanda.repository.ProductRepository;
import org.nevesdev.comanda.repository.StorageRepository;
import org.nevesdev.comanda.service.interfaces.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements ProductServiceInterface {

    //region Dependency injection

    @Autowired
    private ProductRepository productRepository;
    private StorageRepository storageRepository;

    //endregion

    //region Implemented interface methods

    public ProductCreated createProduct(ProductCreate productCreate) {
        String code = RandomStringUtils.randomNumeric(4, 6);
        Product product = new Product(productCreate, code);
        product = productRepository.save(product);
        return new ProductCreated(product, this.createStorage(product));
    }

    public List<ProductCreated> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductCreated> productCreatedList = new ArrayList<>();
        for(Product product : products) {
            ProductCreated productCreated = new ProductCreated(product, this.getStorageQuantity(product));
            productCreatedList.add(productCreated);
        }
        return productCreatedList;
    }

    public ProductCreated getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return new ProductCreated(product, this.getStorageQuantity(product));
    }

    public ProductCreated updateProduct(Long id, ProductUpdate productUpdate) {
        Product product = productRepository.findById(id).orElseThrow();
        product.updateProduct(productUpdate);
        product = productRepository.save(product);
        return new ProductCreated(product, this.getStorageQuantity(product));
    }

    public ProductCreated fastActiveProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setIsActive(!product.getIsActive());
        product = productRepository.save(product);
        return new ProductCreated(product, this.getStorageQuantity(product));
    }


    //endregion

    //region Private methods

    private Integer getStorageQuantity(Product product) {
        Storage storage = storageRepository.findByProduct(product).orElseThrow();
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
}
