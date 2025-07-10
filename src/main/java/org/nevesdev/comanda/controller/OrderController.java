package org.nevesdev.comanda.controller;

import org.nevesdev.comanda.dto.error.ErrorInfo;
import org.nevesdev.comanda.dto.order.OrderCreate;
import org.nevesdev.comanda.model.order.order.PaymentType;
import org.nevesdev.comanda.service.interfaces.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/order")
public class OrderController {

    @Autowired
    private OrderServiceInterface orderServiceInterface;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreate orderCreate) {
        var response = orderServiceInterface.createOrder(orderCreate);
        if(response == null) return ResponseEntity.status(404).body(
                new ErrorInfo(404, "Verifique se a comanda está com os dados completos", "Erro ao criar comanda")
        );
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping(value = "open")
    public ResponseEntity<?> getAllOpenOrders(@RequestParam int page) {
        return ResponseEntity.ok(orderServiceInterface.getAllOpenOrders(page));
    }

    @GetMapping(value = "closed")
    public ResponseEntity<?> getAllClosedOrders(@RequestParam int page) {
        return ResponseEntity.ok(orderServiceInterface.getAllClosedOrders(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(Long id) {
        var response = orderServiceInterface.getOrderById(id);
        if(response == null) return ResponseEntity.status(404).body(new ErrorInfo(
                404, "Verifique se o id está correto", "Erro ao buscar comanda"
        ));
        return ResponseEntity.status(200).body(response);
    }


    @PatchMapping("/insert-item")
    public ResponseEntity<?> insertItem(@RequestParam Long orderId, @RequestParam Long productId) {
        var response = orderServiceInterface.addOrderItem(orderId, productId);
        return response == null ?
                ResponseEntity.status(404).body(new ErrorInfo(404,"Erro ao inserir item na comanda", "Verificar o id do produto e comanda")) :
                ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/delete-item")
    public ResponseEntity<?> deleteItem(@RequestParam Long orderId, @RequestParam Long productId) {
        var response = orderServiceInterface.removeOrderItem(orderId, productId);
        return response == null ?
                ResponseEntity.status(404).body(new ErrorInfo(404,"Erro ao remover item na comanda", "Verificar o id do produto e comanda")) :
                ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/add-item")
    public ResponseEntity<?> addQuantityItem(@RequestParam Long orderId, @RequestParam Long productId) {
        var response = orderServiceInterface.addQuantityOrderItem(orderId, productId);
        return response == null ?
                ResponseEntity.status(404).body(new ErrorInfo(404,"Erro ao adicionar a quantidade no item da comanda", "Verificar o id do produto e comanda")) :
                ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/remove-item")
    public ResponseEntity<?> removeQuantityItem(@RequestParam Long orderId, @RequestParam Long productId) {
        var response = orderServiceInterface.removeQuantityOrderItem(orderId, productId);
        return response == null ?
                ResponseEntity.status(404).body(new ErrorInfo(404,"Erro ao diminuir a quantidade do item na comanda", "Verificar o id do produto e comanda")) :
                ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/close")
    public ResponseEntity<?> closeOrder(@RequestParam Long orderId, @RequestParam PaymentType paymentType) {
        var response = orderServiceInterface.closeOrder(orderId, paymentType);
        return response == null ?
                ResponseEntity.status(404).body(new ErrorInfo(404,"Erro ao fechar a comanda", "Verificar o id da comanda")) :
                ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        var response = orderServiceInterface.deleteOrder(id);
        return response == null ?
                ResponseEntity.status(404).body(new ErrorInfo(404,"Erro ao excluir a comanda", "Verificar o id da comanda")) :
                ResponseEntity.status(200).body(response);
    }
}
