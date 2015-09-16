package com.redhat.brq.integration.switchyard;

import com.redhat.brq.integration.switchyard.models.Order;
import com.redhat.brq.integration.switchyard.status.OrderStatusService;
import com.redhat.brq.integration.switchyard.test.JMSUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.Message;
import org.switchyard.component.bean.config.model.BeanSwitchYardScanner;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.component.test.mixins.naming.NamingMixIn;
import org.switchyard.deploy.internal.Deployment;
import org.switchyard.internal.DefaultMessage;
import org.switchyard.test.*;
import org.switchyard.transform.TransformerRegistry;
import org.switchyard.transform.config.model.TransformSwitchYardScanner;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.redhat.brq.integration.switchyard.test.ModelFactory.createAddress;
import static com.redhat.brq.integration.switchyard.test.ModelFactory.createOrder;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        mixins = {CDIMixIn.class, HTTPMixIn.class, NamingMixIn.class},
        scanners = {BeanSwitchYardScanner.class, TransformSwitchYardScanner.class})
public class Lab03Test {

    private SwitchYardTestKit testKit;
    private TransformerRegistry transformRegistry;
    private Deployment deployment;
    private CDIMixIn cdiMixIn;
    private HTTPMixIn httpMixIn;
    private NamingMixIn namingMixIn;

    public final static QName FROM_ITEMS_JAVA = new QName("", "java:com.redhat.brq.integration.switchyard.models.OrderItem[]");
    public final static QName TO_RESERVE_CSV = new QName("urn:com.redhat.brq.integration.exercise.inventory:1.0", "reserve");

    public final static QName FROM_RESPONSE_CSV = new QName("urn:com.redhat.brq.integration.exercise.inventory:1.0", "response");
    public final static QName TO_RESPONSE_JAVA = new QName("", "java:com.redhat.brq.integration.switchyard.models.InventoryReply[]");

    @ServiceOperation("OrderService.submitOrder")
    private Invoker service;

    @Inject
    private OrderStatusService statusService;

    @BeforeDeploy
    public void deploy() throws Exception {
        JMSUtils.bindConnectionFactory(namingMixIn);
    }

    @Test
    public void testTransformationRequest() {
        Message msg = new DefaultMessage();
        msg.setContent(createAddress());

        assertTrue("Add transformer from: " + FROM_ITEMS_JAVA + " to: " + TO_RESERVE_CSV,
                transformRegistry.hasTransformer(FROM_ITEMS_JAVA, TO_RESERVE_CSV));
    }

    @Test
    public void testTransformationResponse() throws ParserConfigurationException, IOException, SAXException {
        assertTrue("Add transformer from: " + FROM_RESPONSE_CSV + " to: " + TO_RESPONSE_JAVA,
                transformRegistry.hasTransformer(FROM_RESPONSE_CSV, TO_RESPONSE_JAVA));
    }

    @Test
    public void testInventoryReference() throws InterruptedException {
        Order order = createOrder(1L);
        service.sendInOut(order);
        Thread.sleep(2000);
        assertNotEquals("PROCESSED", statusService.find(1L).getInventory());
    }
}
