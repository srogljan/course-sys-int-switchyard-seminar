package com.redhat.brq.integration.switchyard.accounting;

import com.redhat.brq.integration.switchyard.models.InvoiceIssueReply;
import com.redhat.brq.integration.switchyard.models.Order;

public interface AccountingService {
    InvoiceIssueReply account(Order order);
}
