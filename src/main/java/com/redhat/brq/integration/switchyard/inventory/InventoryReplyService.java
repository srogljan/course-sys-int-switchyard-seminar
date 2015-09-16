package com.redhat.brq.integration.switchyard.inventory;

import com.redhat.brq.integration.switchyard.models.InventoryReply;

public interface InventoryReplyService {
    void process(InventoryReply[] reply);
}
