package com.redhat.brq.integration.switchyard.shipment;

import org.switchyard.annotations.Transformer;
import org.w3c.dom.Element;

public final class StatusTransformer {

    @Transformer(from = "{urn:com.redhat.brq.integration.exercise.shipment:1.0}delivery")
    public String transformDeliveryToString(Element from) {
        return from.getAttribute("status");
    }
}
