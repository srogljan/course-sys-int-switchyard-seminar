package com.redhat.brq.integration.switchyard;

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
import org.switchyard.test.*;
import org.switchyard.transform.config.model.TransformSwitchYardScanner;

import javax.inject.Inject;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        mixins = {CDIMixIn.class, HTTPMixIn.class, NamingMixIn.class},
        scanners = {BeanSwitchYardScanner.class, TransformSwitchYardScanner.class})
public class Lab05Test {

    private SwitchYardTestKit testKit;
    private CDIMixIn cdiMixIn;
    private HTTPMixIn httpMixIn;
    private NamingMixIn namingMixIn;
    @ServiceOperation("OrderService")
    private Invoker service;

    @Inject
    private OrderStatusService statusService;

    @BeforeDeploy
    public void dep() throws Exception {
        JMSUtils.bindConnectionFactory(namingMixIn);
    }

    @Test
    public void testSOAPService() throws InterruptedException {
        httpMixIn.postResourceAndTestXML("http://localhost:8080/OrderService/submitOrder", "/xml/soap-order.xml", "/xml/soap-order-response.xml");
        Thread.sleep(2000);
        Status status = statusService.find(1L);
        Assert.assertEquals(1000001L, status.getInvoiceId());
        Assert.assertNotEquals("PROCESSED", status.getInventory());
        Assert.assertEquals("ISSUED", status.getInvoice());
        Assert.assertEquals("OK", status.getShipment());
    }
}
