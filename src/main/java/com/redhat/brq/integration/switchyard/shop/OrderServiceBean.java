package com.redhat.brq.integration.switchyard.shop;

import com.redhat.brq.integration.switchyard.models.Order;
import com.redhat.brq.integration.switchyard.models.OrderItem;
import com.redhat.brq.integration.switchyard.models.Status;
import com.redhat.brq.integration.switchyard.status.OrderStatusService;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.ReferenceInvoker;
import org.switchyard.component.bean.Service;

import javax.inject.Inject;

@Service(OrderService.class)
public class OrderServiceBean implements OrderService {

    @Reference("InventoryService")
    @Inject
    private ReferenceInvoker inventoryService;

    @Reference("ShipmentService")
    @Inject
    private ReferenceInvoker shipmentService;

    @Reference
    @Inject
    private OrderStatusService storageService;

    //@todo(step4) inject reference AccountingService

    @Override
    public String submitOrder(Order order) {
        Status status = new Status(order);
        /*@todo(step4) uncomment
        InvoiceIssueReply invoice = accountingService.account(order);
        status.setInvoiceId(invoice.getInvoiceId());
        status.setInvoice(invoice.getStatus());
        */
        storageService.save(status);

        try {
            inventoryService.newInvocation().setProperty("orderId", Long.toString(order.getId())).invoke(order.getItems().toArray(new OrderItem[0]));
            shipmentService.newInvocation().setProperty("orderId", Long.toString(order.getId())).invoke(order.getAddress());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Long.toString(order.getId());
    }
}
