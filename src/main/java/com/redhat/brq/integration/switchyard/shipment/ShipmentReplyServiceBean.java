package com.redhat.brq.integration.switchyard.shipment;

import com.redhat.brq.integration.switchyard.models.Status;
import com.redhat.brq.integration.switchyard.status.OrderStatusService;
import org.switchyard.Context;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

import javax.inject.Inject;

@Service(ShipmentReplyService.class)
public class ShipmentReplyServiceBean implements ShipmentReplyService {

    @Inject
    private Context context;

    @Inject
    @Reference
    private OrderStatusService statusService;

    @Override
    public void consume(String msg) {
        long id = Long.parseLong(context.getPropertyValue("orderId"));
        Status status = statusService.find(id);
        status.setShipment(msg);
        statusService.save(status);
    }
}
