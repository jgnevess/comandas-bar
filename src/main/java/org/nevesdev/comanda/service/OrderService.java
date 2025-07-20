package org.nevesdev.comanda.service;

import org.nevesdev.comanda.dto.order.OrderCreate;
import org.nevesdev.comanda.dto.order.OrderPreview;
import org.nevesdev.comanda.exceptions.OrderException;
import org.nevesdev.comanda.exceptions.ProductNotFoundException;
import org.nevesdev.comanda.model.order.order.Order;
import org.nevesdev.comanda.model.order.order.PaymentType;
import org.nevesdev.comanda.model.order.order.Status;
import org.nevesdev.comanda.model.order.orderItem.OrderItem;
import org.nevesdev.comanda.model.product.Product;
import org.nevesdev.comanda.model.sale.Sale;
import org.nevesdev.comanda.model.storage.Storage;
import org.nevesdev.comanda.repository.OrderRepository;
import org.nevesdev.comanda.repository.StorageRepository;
import org.nevesdev.comanda.service.interfaces.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SaleService saleService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StorageRepository storageRepository;

    @Override
    public Order createOrder(OrderCreate orderCreate) {
        if(orderCreate.getClientName().isBlank()) throw new OrderException(
                "The client name cannot is empty", 500);
        Order order = new Order(orderCreate);
        order.setTotalPriceOrder();
        order = orderRepository.save(order);
        return order;
    }

    @Override
    public Page<OrderPreview> getAllOpenOrders(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Order> o = orderRepository.findByStatus(Status.OPEN, pageable);
        List<OrderPreview> orderPreview = o.stream().map(OrderPreview::new).toList();
        return new PageImpl<>(
                orderPreview, pageable, o.getTotalElements()
        );
    }

    @Override
    public Page<OrderPreview> getAllClosedOrders(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Order> o = orderRepository.findByStatus(Status.CLOSED, pageable);
        List<OrderPreview> orderPreview = o.stream().map(OrderPreview::new).toList();
        return new PageImpl<>(
                orderPreview, pageable, o.getTotalElements()
        );
    }



    @Override
    public Order getOrderById(Long id) {
        Order order = this.orderRepository.findById(id).orElse(null);
        if(order == null) throw new OrderException("Order not found", 404);
        order.setTotalPriceOrder();
        return order;
    }

    @Override
    public Page<OrderPreview> getAllOrdersCloseBetweenDate(int page, int pageSize) {
        LocalDate startDate = LocalDate.now();
        LocalDateTime endDate = LocalDateTime.now();
        List<Order> orders = orderRepository.findAllOrdersByOrderDateTimeBetweenAndStatus(startDate.atStartOfDay(), endDate, Status.CLOSED);
        List<OrderPreview> previews = orders.stream().map(OrderPreview::new).toList();
        int start = page * pageSize;
        int end = Math.min(start + pageSize, orders.size());
        return new PageImpl<>(
                    previews.subList(start, end),
                    PageRequest.of(page, pageSize),
                    previews.size()
                );
    }

    @Override
    public Order addItemOnOrder(Long id, Long productId) {
        Product product = productService.getProductById(productId).getProduct();
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderException("Order not found", 404));
        Storage storage = storageRepository.findByProduct(product).orElseThrow(() -> new ProductNotFoundException("Storage error", 500));
        if(storage.getQuantity() <=0) throw new ProductNotFoundException("Error Quantity", 500);

        List<OrderItem> orderItems = order.getItems();
        OrderItem orderItem = new OrderItem(product);
        for (OrderItem orderItem1 : orderItems) {
            if(orderItem1.getProductId().equals(product.getId())) {
                addQuantityOnOrderItem(id, productId);
                return order;
            }
        }
        orderItem.setQuantity(1);
        orderItems.add(orderItem);
        order.setItems(orderItems);
        storage.setQuantity(storage.getQuantity() - 1);
        order.setTotalPriceOrder();
        storageRepository.save(storage);
        return orderRepository.save(order);
    }

    @Override
    public Order removeItemOnOrder(Long id, Long productId) {
        Product product = productService.getProductById(productId).getProduct();
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null) throw new OrderException("Order not found", 404);
        List<OrderItem> orderItems = order.getItems();
        for(OrderItem orderItem1 : orderItems) {
            if(orderItem1.getProductId().equals(product.getId())) {
                orderItems.remove(orderItem1);
                break;
            }
        }
        order.setItems(orderItems);
        Storage storage = storageRepository.findByProduct(product).orElse(null);
        if(storage == null) throw new ProductNotFoundException("Storage error", 500);
        storage.setQuantity(storage.getQuantity() + 1);
        order.setTotalPriceOrder();
        storageRepository.save(storage);
        return orderRepository.save(order);
    }


    @Override
    public OrderItem addQuantityOnOrderItem(Long id, Long productId) {
        System.out.println(productId);
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null) throw new OrderException("Order not found", 404);
        List<OrderItem> orderItems = order.getItems();
        OrderItem orderItem = new OrderItem();
        for(OrderItem o: orderItems) {
            if(o.getProductId().equals(productId)) {
                Product product = productService.getProductById(productId).getProduct();
                Storage storage = storageRepository.findByProduct(product).orElseThrow(() -> new ProductNotFoundException("Storage error", 500));
                if(storage.getQuantity() <=0) throw new ProductNotFoundException("Error Quantity", 500);
                storage.setQuantity(storage.getQuantity() - 1);
                o.setQuantity(o.getQuantity() + 1);
                storageRepository.save(storage);
                orderItem = o;
                break;
            }
        }
        order.setItems(orderItems);
        order.setTotalPriceOrder();
        orderRepository.save(order);
        return orderItem;
    }

    @Override
    public OrderItem removeQuantityOnOrderItem(Long id, Long productId) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null) throw new OrderException("Order not found", 404);
        List<OrderItem> orderItems = order.getItems();
        OrderItem orderItem = new OrderItem();
        for(OrderItem o: orderItems) {
            if(o.getProductId().equals(productId)) {
                Product product = productService.getProductById(productId).getProduct();
                Storage storage = storageRepository.findByProduct(product).orElse(null);
                if(storage == null) throw new ProductNotFoundException("Storage error", 500);
                storage.setQuantity(storage.getQuantity() + 1);
                o.setQuantity(o.getQuantity() - 1);
                storageRepository.save(storage);
                orderItem = o;
                break;
            }
        }
        order.setItems(orderItems);
        order.setTotalPriceOrder();
        orderRepository.save(order);
        return orderItem;
    }

    @Override
    public Sale closeOrder(Long id, PaymentType paymentType) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null) throw new OrderException("Order not found", 404);
        if(order.getStatus().equals(Status.CLOSED)) throw new OrderException("Order is closed", 406);
        order.setPaymentType(paymentType);
        order.setStatus(Status.CLOSED);
        order.setTotalPriceOrder();
        order = orderRepository.save(order);
        return saleService.saveSale(order);
    }

    @Override
    public Boolean deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderException("Order not found", 404));
        if(order.getStatus().equals(Status.CLOSED)) throw new OrderException("Order is closed", 406);
        for(var i : order.getItems()) {
            Product p = productService.getProductById(i.getProductId()).getProduct();
            Storage s = storageRepository.findByProduct(p).orElseThrow(() -> new ProductNotFoundException("Product not found", 500));
            s.setQuantity(s.getQuantity() + i.getQuantity());
            storageRepository.save(s);
        }
        orderRepository.delete(order);
        return true;
    }
}
