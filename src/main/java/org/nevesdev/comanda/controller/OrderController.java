package org.nevesdev.comanda.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.nevesdev.comanda.dto.order.OrderCreate;
import org.nevesdev.comanda.dto.order.OrderPreview;
import org.nevesdev.comanda.model.order.order.Order;
import org.nevesdev.comanda.model.order.order.PaymentType;
import org.nevesdev.comanda.model.order.orderItem.OrderItem;
import org.nevesdev.comanda.model.sale.Sale;
import org.nevesdev.comanda.service.interfaces.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/order")
@SecurityRequirement(name = "bearer-key")
public class OrderController {

    @Autowired
    private OrderServiceInterface orderServiceInterface;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreate orderCreate) {
        return ResponseEntity.status(201).body(orderServiceInterface.createOrder(orderCreate));
    }

    @GetMapping(value = "open")
    public ResponseEntity<Page<OrderPreview>> getAllOpenOrders(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(orderServiceInterface.getAllOpenOrders(page, pageSize));
    }

    @GetMapping(value = "closed")
    public ResponseEntity<Page<OrderPreview>> getAllClosedOrders(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(orderServiceInterface.getAllClosedOrders(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(Long id) {
        return ResponseEntity.status(200).body(orderServiceInterface.getOrderById(id));
    }


    @PatchMapping("/insert-item")
    public ResponseEntity<Order> insertItemOnOrder(@RequestParam Long orderId, @RequestParam Long productId) {
        return ResponseEntity.status(200).body(orderServiceInterface.addItemOnOrder(orderId, productId));
    }

    @PatchMapping("/delete-item")
    public ResponseEntity<Order> deleteItemOnOrder(@RequestParam Long orderId, @RequestParam Long productId) {
        return ResponseEntity.status(200).body(orderServiceInterface.removeItemOnOrder(orderId, productId));
    }

    @PatchMapping("/add-item-quantity")
    public ResponseEntity<OrderItem> addQuantityOnItem(@RequestParam Long orderId, @RequestParam Long productId) {
        return ResponseEntity.status(200).body(orderServiceInterface.addQuantityOnOrderItem(orderId, productId));
    }

    @PatchMapping("/remove-item-quantity")
    public ResponseEntity<OrderItem> removeQuantityOnItem(@RequestParam Long orderId, @RequestParam Long productId) {
        return ResponseEntity.status(200).body(orderServiceInterface.removeQuantityOnOrderItem(orderId, productId));
    }

    @PatchMapping("/close")
    public ResponseEntity<Sale> closeOrder(@RequestParam Long orderId, @RequestParam PaymentType paymentType) {
        return ResponseEntity.status(200).body(orderServiceInterface.closeOrder(orderId, paymentType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.status(404).build();
    }
}
