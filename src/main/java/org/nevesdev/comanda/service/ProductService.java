package org.nevesdev.comanda.service;

import jakarta.validation.UnexpectedTypeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.nevesdev.comanda.dto.product.*;
import org.nevesdev.comanda.exceptions.NotValidException;
import org.nevesdev.comanda.exceptions.ProductNotFoundException;
import org.nevesdev.comanda.model.product.Product;
import org.nevesdev.comanda.model.storage.Storage;
import org.nevesdev.comanda.repository.ProductRepository;
import org.nevesdev.comanda.repository.StorageRepository;
import org.nevesdev.comanda.service.interfaces.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

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
        try {
            String code = RandomStringUtils.randomNumeric(4, 6);
            Product product = new Product(productCreate, code);
            product = productRepository.save(product);
            return new ProductCreated(product, this.createStorage(product));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException("Erro ao criar produto", 400);
        }
    }

    @Override
    public Page<ProductCreated> getAllProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> new ProductCreated(product, this.getStorageQuantity(product)));
    }

    @Override
    public ProductCreated getById(Long id) {
        Product product = this.getProduct(id);
        int quantity = this.getStorageQuantity(product);
        return new ProductCreated(product, quantity);
    }

    @Override
    public ProductCreated updateProduct(Long id, ProductUpdate productUpdate) {
        Product product = this.getProduct(id);
        product.updateProduct(productUpdate);
        product = productRepository.save(product);
        return new ProductCreated(product, this.getStorageQuantity(product));
    }

    @Override
    public ProductCreated fastActiveProduct(Long id) {
        Product product = this.getProduct(id);
        product.setIsActive(!product.getIsActive());
        product = productRepository.save(product);
        return new ProductCreated(product, this.getStorageQuantity(product));
    }

    @Override
    public ProductCreated addProduct(Long id, Integer quantity) {
        Product product = this.getProduct(id);
        Storage storage = this.getStorage(id);
        storage.setQuantity(storage.getQuantity() + quantity);
        storage = storageRepository.save(storage);
        return new ProductCreated(product, storage.getQuantity());
    }

    @Override
    public ProductCreated removeProduct(Long id, Integer quantity) {
        Product product = this.getProduct(id);
        Storage storage = this.getStorage(id);
        int value = storage.getQuantity() - quantity;
        if(value < 0) throw new ProductNotFoundException("Sem produto", 404);
        storage.setQuantity(value);
        storage = storageRepository.save(storage);
        return new ProductCreated(product, storage.getQuantity());
    }

    @Override
    public Page<ProductCreated> getAllActive(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Product> products = productRepository.findByIsActiveTrue(pageable);
        return products.map(product -> {
            return new ProductCreated(product, this.getStorageQuantity(product));
        });
    }

    @Override
    public Page<ProductCreated> getAllInactive(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Product> products = productRepository.findByIsActiveFalse(pageable);
        return products.map(product -> {
            return new ProductCreated(product, this.getStorageQuantity(product));
        });
    }

    @Override
    public List<ProductSelect> getAllActive() {
        List<Product> products = productRepository.findAll();
        return products.stream().filter(Product::getIsActive).map(ProductSelect::new).toList();
    }

    @Override
    public List<ProductSelect> getAllByDescription(String description) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(Product::getIsActive)
                .filter(product -> product.getDescription()
                        .toLowerCase()
                        .contains(description.toLowerCase()))
                .map(ProductSelect::new).toList();
    }

    //endregion

    //region Private methods

    private Integer getStorageQuantity(Product product) {
        Storage storage = storageRepository.findByProduct(product).orElseThrow(() -> new ProductNotFoundException("Sem quantidade", 500));
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

    private Storage getStorage(long id) {
        Product product = this.getProduct(id);
        return storageRepository.findByProduct(product).orElseThrow(() -> new ProductNotFoundException("Storage error", 404));
    }

    private Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found", 404));
    }

    //endregion

    protected ProductQuantity getProductById(Long id) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Aqui sera?", 404));
        return new ProductQuantity(p, this.getStorageQuantity(p));
    }
}
