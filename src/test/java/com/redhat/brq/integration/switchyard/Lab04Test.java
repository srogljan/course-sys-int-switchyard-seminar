package com.redhat.brq.integration.switchyard;

import com.redhat.brq.integration.switchyard.models.Order;
import com.redhat.brq.integration.switchyard.models.Status;
import com.redhat.brq.integration.switchyard.status.OrderStatusService;
import com.redhat.brq.integration.switchyard.test.JMSUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.bean.config.model.BeanSwitchYardScanner;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.component.test.mixins.naming.NamingMixIn;
import org.switchyard.deploy.internal.Deployment;
import org.switchyard.test.*;
import org.switchyard.transform.TransformerRegistry;
import org.switchyard.transform.config.model.TransformSwitchYardScanner;

import javax.inject.Inject;

import static com.redhat.brq.integration.switchyard.test.ModelFactory.createOrder;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        mixins = {CDIMixIn.class, HTTPMixIn.class, NamingMixIn.class},
        scanners = {BeanSwitchYardScanner.class, TransformSwitchYardScanner.class})
public class Lab04Test {

    private SwitchYardTestKit testKit;
    private TransformerRegistry transformRegistry;
    private Deployment deployment;
    private CDIMixIn cdiMixIn;
    private HTTPMixIn httpMixIn;
    private NamingMixIn namingMixIn;

    @ServiceOperation("OrderService.submitOrder")
    private Invoker service;

    @Inject
    private OrderStatusService statusService;

    @BeforeDeploy
    public void deploy() throws Exception {
        JMSUtils.bindConnectionFactory(namingMixIn);
    }

    @Test
    public void testInventoryReference() throws InterruptedException {
        Order order = createOrder(1L);
        service.sendInOut(order);
        Thread.sleep(2000);
        Status status = statusService.find(1L);
        Assert.assertEquals(1000001L, status.getInvoiceId());
        Assert.assertEquals("ISSUED", status.getInvoice());
    }
}
