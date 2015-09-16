package com.redhat.brq.integration.switchyard.inventory;

import org.switchyard.Exchange;
import org.switchyard.Message;
import org.switchyard.component.camel.common.composer.CamelBindingData;
import org.switchyard.component.camel.common.composer.CamelMessageComposer;

public class MessageComposer extends CamelMessageComposer {

    @Override
    public CamelBindingData decompose(Exchange exchange, CamelBindingData target) throws Exception {
        //@todo(step3) set file name property (org.apache.camel.Exchange.FILE_NAME) by property orderId.
        // properties are contained in exchange context
        return super.decompose(exchange, target);
    }

    @Override
    public Message compose(CamelBindingData source, Exchange exchange) throws Exception {
        //@todo(step3) parse id from file name (header org.apache.camel.Exchange.FILE_NAME_ONLY) and save it in orderId
        Message msg = super.compose(source, exchange);
        return msg;
    }
}
