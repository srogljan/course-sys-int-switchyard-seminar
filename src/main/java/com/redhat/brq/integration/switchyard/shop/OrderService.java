package com.redhat.brq.integration.switchyard.shop;

import com.redhat.brq.integration.switchyard.models.Order;

public interface OrderService {
    String submitOrder(Order order);
}
