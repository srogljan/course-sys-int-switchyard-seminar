package com.redhat.brq.integration.switchyard.inventory;

import org.switchyard.Context;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

import com.redhat.brq.integration.switchyard.models.InventoryReply;
import com.redhat.brq.integration.switchyard.models.Status;
import com.redhat.brq.integration.switchyard.status.OrderStatusService;

import javax.inject.Inject;

import java.util.Arrays;

@Service(InventoryReplyService.class)
public class InventoryReplyServiceBean implements InventoryReplyService {

	@Inject
	private Context context;

	@Inject
	@Reference
	private OrderStatusService statusService;

	@Override
	public void process(InventoryReply[] reply) {
		long id = Long.parseLong(context.getPropertyValue("orderId"));
		Status status = statusService.find(id);
		status.setInventory(Arrays.stream(reply).anyMatch((InventoryReply x) -> x.getReserved() == 0) ? "FAIL" : "OK");
		statusService.save(status);
	}
}
