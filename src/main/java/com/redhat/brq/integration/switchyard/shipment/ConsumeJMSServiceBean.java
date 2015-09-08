package com.redhat.brq.integration.switchyard.shipment;

import org.switchyard.Context;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.brq.integration.switchyard.models.InventoryReply;
import com.redhat.brq.integration.switchyard.models.Status;
import com.redhat.brq.integration.switchyard.status.OrderStatusService;

import javax.inject.Inject;

import java.util.Arrays;

@Service(ConsumeJMSService.class)
public class ConsumeJMSServiceBean implements ConsumeJMSService {

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
