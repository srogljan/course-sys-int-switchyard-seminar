package com.redhat.brq.integration.switchyard.shop;

import org.switchyard.annotations.Transformer;

public class StatusTransformer {

    @Transformer(to = "{urn:com.redhat.brq.integration.switchyard:1.0}orderResponse")
    public String transformDeliveryToString(String from) {
        return "<status>" + from + "</status>";
    }
}
