package com.redhat.brq.integration.switchyard;

import com.redhat.brq.integration.switchyard.models.Order;
import com.redhat.brq.integration.switchyard.models.Status;
import com.redhat.brq.integration.switchyard.test.JMSUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.bean.config.model.BeanSwitchYardScanner;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.component.test.mixins.naming.NamingMixIn;
import org.switchyard.test.*;
import org.switchyard.transform.config.model.TransformSwitchYardScanner;

import static com.redhat.brq.integration.switchyard.test.ModelFactory.createOrder;
import static org.junit.Assert.assertEquals;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        mixins = {CDIMixIn.class, HTTPMixIn.class, NamingMixIn.class},
        scanners = {BeanSwitchYardScanner.class, TransformSwitchYardScanner.class})
public class Lab01Test {

    @ServiceOperation("OrderService.submitOrder")
    private Invoker service;

    @ServiceOperation("OrderStatusService.find")
    private Invoker store;

    private NamingMixIn namingMixIn;

    @BeforeDeploy
    public void deploy() throws Exception {
        org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory();
        JMSUtils.bindConnectionFactory(namingMixIn);
    }

    @Test
    public void testShipmentReference() throws InterruptedException {
        Order order = createOrder(1L);
        service.sendInOut(createOrder(1L));
        assertEquals(order.getId(), store.sendInOut(1L).getContent(Status.class).getOrder().getId());
    }
}
