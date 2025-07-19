package org.nevesdev.comanda.service.interfaces;

import org.nevesdev.comanda.dto.order.OrderCreate;
import org.nevesdev.comanda.dto.order.OrderPreview;
import org.nevesdev.comanda.model.order.order.Order;
import org.nevesdev.comanda.model.order.order.PaymentType;
import org.nevesdev.comanda.model.order.orderItem.OrderItem;
import org.nevesdev.comanda.model.sale.Sale;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface OrderServiceInterface {

    Order createOrder(OrderCreate orderCreate);

    Page<OrderPreview> getAllOpenOrders(int page, int pageSize);
    Page<OrderPreview> getAllClosedOrders(int page, int pageSize);
    Order getOrderById(Long id);
    Page<OrderPreview> getAllOrdersCloseBetweenDate(int page, int pageSize);

    Order addItemOnOrder(Long id, Long productId);
    Order removeItemOnOrder(Long id, Long productId);
    OrderItem addQuantityOnOrderItem(Long id, Long productId);
    OrderItem removeQuantityOnOrderItem(Long id, Long productId);

    Sale closeOrder(Long id, PaymentType paymentType);
    Boolean deleteOrder(Long id);


}
