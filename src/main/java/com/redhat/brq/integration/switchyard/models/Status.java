package com.redhat.brq.integration.switchyard.models;

import java.io.Serializable;

public class Status implements Serializable {

    private long invoiceId;
    private Order order;
    private String invoice;
    private String inventory;
    private String shipment;

    public Status() {
    }

    public Status(Order order) {
        this.order = order;
        invoice = "PROCESSED";
        inventory = "PROCESSED";
        shipment = "PROCESSED";
    }

    public Status(long invoiceId, Order order, String invoice, String inventory, String shipment) {
        this.invoiceId = invoiceId;
        this.order = order;
        this.invoice = invoice;
        this.inventory = inventory;
        this.shipment = shipment;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    @Override
    public String toString() {
        return "Status{" +
                "invoiceId=" + invoiceId +
                ", submitOrder=" + order +
                ", invoice='" + invoice + '\'' +
                ", inventory='" + inventory + '\'' +
                ", shipment='" + shipment + '\'' +
                '}';
    }
}
