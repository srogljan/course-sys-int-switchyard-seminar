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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

import static com.redhat.brq.integration.switchyard.test.ModelFactory.createAddress;
import static com.redhat.brq.integration.switchyard.test.ModelFactory.createOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        mixins = {CDIMixIn.class, HTTPMixIn.class, NamingMixIn.class},
        scanners = {BeanSwitchYardScanner.class, TransformSwitchYardScanner.class})
public class Lab02Test {

    private SwitchYardTestKit testKit;
    private TransformerRegistry transformRegistry;
    private Deployment deployment;
    private CDIMixIn cdiMixIn;
    private HTTPMixIn httpMixIn;
    private NamingMixIn namingMixIn;

    public final static QName FROM_ADDRESS_JAVA = new QName("", "java:com.redhat.brq.integration.switchyard.models.Address");
    public final static QName TO_ADDRESS_XML = new QName("urn:com.redhat.brq.integration.exercise.shipment:1.0", "address");

    public final static QName FROM_RESPONSE_JMS = new QName("urn:com.redhat.brq.integration.exercise.shipment:1.0", "delivery");
    public final static QName TO_RESPONSE_BEAN = new QName("", "java:java.lang.String");

    @ServiceOperation("OrderService.submitOrder")
    private Invoker service;

    @Inject
    private OrderStatusService storage;

    @BeforeDeploy
    public void deploy() throws Exception {
        JMSUtils.bindConnectionFactory(namingMixIn);
    }

    @Test
    public void testTransformationRequest() {
        Message msg = new DefaultMessage();
        msg.setContent(createAddress());

        assertTrue("Add transformer from: " + FROM_ADDRESS_JAVA + " to: " + TO_ADDRESS_XML,
                transformRegistry.hasTransformer(FROM_ADDRESS_JAVA, TO_ADDRESS_XML));

        transformRegistry.getTransformSequence(FROM_ADDRESS_JAVA, TO_ADDRESS_XML).apply(msg, transformRegistry);

        testKit.compareXMLToResource(msg.getContent(String.class), "/xml/address-message.xml");
    }

    @Test
    public void testTransformationResponse() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<delivery status=\"OK\"/>"));

        Document doc = db.parse(is);
        Message msg = new DefaultMessage();
        msg.setContent(doc.getDocumentElement());

        assertTrue("Add transformer from: " + FROM_RESPONSE_JMS + " to: " + TO_RESPONSE_BEAN,
                transformRegistry.hasTransformer(FROM_RESPONSE_JMS, TO_RESPONSE_BEAN));

        transformRegistry.getTransformSequence(FROM_RESPONSE_JMS, TO_RESPONSE_BEAN).apply(msg, transformRegistry);

        assertEquals("OK", msg.getContent(String.class));
    }

    @Test
    public void testShipment() throws InterruptedException {
        Order order = createOrder(1L);
        service.sendInOut(order);
        Thread.sleep(2000);
        assertEquals("OK", storage.find(1L).getShipment());
    }
}
