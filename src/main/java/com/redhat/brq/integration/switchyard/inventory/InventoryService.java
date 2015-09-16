package com.redhat.brq.integration.switchyard.inventory;

import com.redhat.brq.integration.switchyard.models.OrderItem;

public interface InventoryService {
    void reserve(OrderItem[] items);
}
