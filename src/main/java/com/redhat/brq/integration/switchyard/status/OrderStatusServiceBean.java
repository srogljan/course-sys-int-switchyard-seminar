package com.redhat.brq.integration.switchyard.status;

import com.redhat.brq.integration.switchyard.models.Status;
import org.switchyard.component.bean.Service;

import java.util.HashMap;
import java.util.Map;

@Service(OrderStatusService.class)
public class OrderStatusServiceBean implements OrderStatusService {

    public static Map<Long, Status> storage = new HashMap<>();

    @Override
    public void save(Status status) {
        storage.put(status.getOrder().getId(), status);
    }

    @Override
    public Status find(Long id) {
        return storage.get(id);
    }
}
