package com.redhat.brq.integration.switchyard.status;

import com.redhat.brq.integration.switchyard.models.Status;

public interface OrderStatusService {
    void save(Status order);

    Status find(Long id);
}
