package org.nevesdev.comanda.service.interfaces;

import org.nevesdev.comanda.dto.order.OrderCreate;
import org.nevesdev.comanda.dto.order.OrderPreview;
import org.nevesdev.comanda.model.order.order.Order;
import org.nevesdev.comanda.model.order.order.PaymentType;
import org.nevesdev.comanda.model.order.orderItem.OrderItem;
import org.nevesdev.comanda.model.sale.Sale;
import org.springframework.data.domain.Page;

public interface OrderServiceInterface {

    Order createOrder(OrderCreate orderCreate);

    Page<OrderPreview> getAllOpenOrders(int page);
    Page<OrderPreview> getAllClosedOrders(int page);
    Order getOrderById(Long id);

    Order addOrderItem(Long id, Long productId);
    Order removeOrderItem(Long id, Long productId);
    OrderItem addQuantityOrderItem(Long id, Long productId);
    OrderItem removeQuantityOrderItem(Long id, Long productId);

    Sale closeOrder(Long id, PaymentType paymentType);
    Boolean deleteOrder(Long id);


}
