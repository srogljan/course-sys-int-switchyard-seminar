package com.redhat.brq.integration.switchyard.shipment;

import com.redhat.brq.integration.switchyard.models.Address;

public interface ShipmentService {
    void delivery(Address address);
}
