package com.redhat.brq.integration.switchyard.shipment;

public interface ShipmentReplyService {
    void consume(String status);
}
