/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.brq.integration.switchyard.models;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _Address_QNAME = new QName("urn:com.redhat.brq.integration.switchyard:1.0", "address");
    private final static QName _Order_QNAME = new QName("urn:com.redhat.brq.integration.switchyard:1.0", "order");
    private final static QName _OrderItem_QNAME = new QName("urn:com.redhat.brq.integration.switchyard:1.0", "item");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Address }
     */
    public Address createAddress() {
        return new Address();
    }

    public Order createOrder() {
        return new Order();
    }

    public OrderItem createOrderItem() {
        return new OrderItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Address }{@code >}}
     */
    @XmlElementDecl(name = "address")
    public JAXBElement<Address> createAddress(Address value) {
        return new JAXBElement<Address>(_Address_QNAME, Address.class, null, value);
    }

    @XmlElementDecl(name = "order", namespace = "urn:com.redhat.brq.integration.switchyard:1.0")
    public JAXBElement<Order> createOrder(Order value) {
        return new JAXBElement<Order>(_Order_QNAME, Order.class, null, value);
    }

    @XmlElementDecl(name = "item")
    public JAXBElement<OrderItem> createOrder(OrderItem value) {
        return new JAXBElement<OrderItem>(_OrderItem_QNAME, OrderItem.class, null, value);
    }
}
