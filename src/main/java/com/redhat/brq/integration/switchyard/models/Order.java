package com.redhat.brq.integration.switchyard.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "order", namespace = "urn:com.redhat.brq.integration.switchyard")
public class Order implements Serializable {
    private static final long serialVersionUID = 9025355997861450821L;

    private long id;

    private List<OrderItem> items = new ArrayList<>();

    private Address address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name = "item")
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @XmlTransient
    public double getTotalPrice() {
        return items.stream().mapToDouble(OrderItem::getPrice).sum();
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", items=" + items + ", address=" + address
                + "]";
    }
}
