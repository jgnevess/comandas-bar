package org.nevesdev.comanda.service;

import org.nevesdev.comanda.dto.order.OrderCreate;
import org.nevesdev.comanda.dto.order.OrderList;
import org.nevesdev.comanda.model.order.order.Order;
import org.nevesdev.comanda.model.order.order.PaymentType;
import org.nevesdev.comanda.model.order.order.Status;
import org.nevesdev.comanda.model.order.orderItem.OrderItem;
import org.nevesdev.comanda.model.product.Product;
import org.nevesdev.comanda.model.sale.Sale;
import org.nevesdev.comanda.repository.OrderRepository;
import org.nevesdev.comanda.repository.SaleRepository;
import org.nevesdev.comanda.service.interfaces.OrderServiceInterface;
import org.nevesdev.comanda.service.interfaces.SaleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SaleService saleService;
    @Autowired
    private ProductService productService;

    @Override
    public Order createOrder(OrderCreate orderCreate) {
        Order order = new Order(orderCreate);
        order = orderRepository.save(order);
        return order;
    }

    @Override
    public Page<OrderList> getAllOpenOrders(int page) {
        Page<Order> orders = this.getAllOrders(page);
        return orders.map(order -> {
            if(!order.getStatus().equals(Status.OPEN)) {
                return null;
            }
            order.setTotalPriceOrder();
            return new OrderList(order);
        });
    }

    @Override
    public Page<OrderList> getAllClosedOrders(int page) {
        Page<Order> orders = this.getAllOrders(page);
        return orders.map(order -> {
            if(!order.getStatus().equals(Status.CLOSED)) {
                return null;
            }
            order.setTotalPriceOrder();
            return new OrderList(order);
        });
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = this.orderRepository.findById(id).orElse(null);
        if(order == null) return null;
        order.setTotalPriceOrder();
        return order;
    }

    @Override
    public Order addOrderItem(Long id, Long productId) {
        Product product = productService.getProductById(productId).getProduct();
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;
        List<OrderItem> orderItems = order.getItems();
        OrderItem orderItem = new OrderItem(product);
        orderItem.setQuantity(1);
        orderItems.add(orderItem);
        order.setItems(orderItems);
        return orderRepository.save(order);
    }

    @Override
    public Order removeOrderItem(Long id, Long productId) {
        Product product = productService.getProductById(productId).getProduct();
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;
        List<OrderItem> orderItems = order.getItems();
        orderItems.remove(new OrderItem(product));
        order.setItems(orderItems);
        return orderRepository.save(order);
    }


    @Override
    public OrderItem addQuantityOrderItem(Long id, Long productId) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;
        List<OrderItem> orderItems = order.getItems();
        OrderItem orderItem = new OrderItem();
        for(OrderItem o: orderItems) {
            if(o.getProductId().equals(productId)) {
                o.setQuantity(o.getQuantity() + 1);
                orderItem = o;
                break;
            }
        }
        order.setItems(orderItems);
        orderRepository.save(order);
        return orderItem;
    }

    @Override
    public OrderItem removeQuantityOrderItem(Long id, Long productId) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;
        List<OrderItem> orderItems = order.getItems();
        OrderItem orderItem = new OrderItem();
        for(OrderItem o: orderItems) {
            if(o.getProductId().equals(productId)) {
                o.setQuantity(o.getQuantity() - 1);
                orderItem = o;
                break;
            }
        }
        order.setItems(orderItems);
        orderRepository.save(order);
        return orderItem;
    }

    @Override
    public Sale closeOrder(Long id, PaymentType paymentType) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null || order.getStatus().equals(Status.CLOSED)) return null;
        order.setPaymentType(paymentType);
        order.setStatus(Status.CLOSED);
        order.setTotalPriceOrder();
        order = orderRepository.save(order);
        return saleService.saveSale(order);
    }

    @Override
    public Boolean deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null || order.getStatus().equals(Status.CLOSED)) return false;
        orderRepository.delete(order);
        return true;
    }

    private Page<Order> getAllOrders(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        return orderRepository.findAll(pageable);
    }
}
