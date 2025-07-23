package org.nevesdev.comanda.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nevesdev.comanda.dto.product.ProductCreate;
import org.nevesdev.comanda.dto.product.ProductCreated;
import org.nevesdev.comanda.exceptions.NotValidException;
import org.nevesdev.comanda.exceptions.ProductNotFoundException;
import org.nevesdev.comanda.model.product.Category;
import org.nevesdev.comanda.model.product.Product;
import org.nevesdev.comanda.model.storage.Storage;
import org.nevesdev.comanda.repository.ProductRepository;
import org.nevesdev.comanda.repository.StorageRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private ProductService productService;

    ProductCreate productCreate = new ProductCreate();
    Product product = new Product();
    Storage storage = new Storage();

    @BeforeEach
    void setProductCreate() {
        productCreate.setDescription("Cerveja");
        productCreate.setOffPrice(10D);
        productCreate.setSellingPrice(10D);
        productCreate.setCostPrice(8D);
        product = new Product(productCreate, "1234");
        product.setId(1L);
        storage.setQuantity(10);
        storage.setProduct(product);
        storage.setMovementDate(LocalDateTime.now());
        storage.setId(1L);
    }

    @Test
    void getById_shouldReturnProductCreated_whenProductExists() {

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(storageRepository.findByProduct(product)).thenReturn(Optional.of(storage));

        ProductCreated result = productService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void getById_ThrowsNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getById(1L));
    }

    @Test
    void getById_ThrowsNotFoundExceptionCorrectMessageAndStatusCode() {
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getById(id));

        assertEquals("Product not found", exception.getMessage());
        assertEquals(404, exception.getStatus());
    }

    @Test
    void createProduct_shouldReturnProductCreated_whenSuccess() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(storageRepository.save(any(Storage.class))).thenReturn(storage);

        ProductCreated result = productService.createProduct(productCreate);

        assertNotNull(result);
        assertEquals("Cerveja", result.getDescription());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void createProduct_ThrowNotValidException() {
        when(productRepository.save(any(Product.class))).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(NotValidException.class, () -> productService.createProduct(productCreate));
    }

    @Test
    void createProduct_ThrowNotValidExceptionCorrectMessageAndStatusCode() {
        when(productRepository.save(any(Product.class))).thenThrow(new DataIntegrityViolationException(""));

        NotValidException exception = assertThrows(NotValidException.class,
                () -> productService.createProduct(productCreate));

        assertEquals("Erro ao criar produto", exception.getMessage());
        assertEquals(400, exception.getStatus());

    }

    @Test
    void getAll_shouldReturnPage() {
        List<Product> mockProducts = List.of(product);
        Page<Product> mockProductPage = new PageImpl<>(mockProducts);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        when(productRepository.findAll(pageable)).thenReturn(mockProductPage);
        when(storageRepository.findByProduct(product)).thenReturn(Optional.of(storage));
        Page<ProductCreated> result = productService.getAllProducts(0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals(new ProductCreated(product, storage.getQuantity()), result.getContent().get(0));
    }

    @Test
    void getAll_shouldThrowProductNotFoundExceptionAndCorrectMessage() {
        List<Product> mockProducts = List.of(product);
        Page<Product> mockProductPage = new PageImpl<>(mockProducts);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        when(productRepository.findAll(pageable)).thenReturn(mockProductPage);

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class, () -> productService.getAllProducts(0, 10));

        assertEquals("Sem quantidade", ex.getMessage());
    }
}