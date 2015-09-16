package com.redhat.brq.integration.switchyard.models;

import java.io.Serializable;

public class InvoiceIssueReply implements Serializable {
    private static final long serialVersionUID = 4770193081968215697L;
    private long invoiceId;
    private Order order;
    private String status;

    public InvoiceIssueReply() {
    }

    public InvoiceIssueReply(long invoiceId, Order order, String status) {
        super();
        this.invoiceId = invoiceId;
        this.order = order;
        this.status = status;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "InvoiceIssueReply [invoiceId=" + invoiceId + ", submitOrder=" + order
                + ", status=" + status + "]";
    }
}
