package com.redhat.brq.integration.switchyard.shop;

import com.redhat.brq.integration.switchyard.models.Order;

public interface ShopService {
	String order(Order order);
}
