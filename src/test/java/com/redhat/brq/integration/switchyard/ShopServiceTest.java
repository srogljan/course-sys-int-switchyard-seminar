package com.redhat.brq.integration.switchyard;

import org.apache.activemq.ActiveMQSslConnectionFactory;

import org.switchyard.component.bean.config.model.BeanSwitchYardScanner;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.component.test.mixins.naming.NamingMixIn;
import org.switchyard.test.BeforeDeploy;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.test.SwitchYardTestKit;
import org.switchyard.transform.config.model.TransformSwitchYardScanner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.brq.integration.switchyard.models.Address;
import com.redhat.brq.integration.switchyard.models.Order;
import com.redhat.brq.integration.switchyard.models.OrderItem;
import com.redhat.brq.integration.switchyard.status.OrderStatusService;

import javax.inject.Inject;

import java.util.LinkedList;
import java.util.Properties;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
		mixins = {CDIMixIn.class, HTTPMixIn.class, NamingMixIn.class},
		scanners = {BeanSwitchYardScanner.class, TransformSwitchYardScanner.class})
public class ShopServiceTest {

	private SwitchYardTestKit testKit;
	private CDIMixIn cdiMixIn;
	private HTTPMixIn httpMixIn;
	private NamingMixIn namingMixIn;
	@ServiceOperation("ShopService")
	private Invoker service;

	@Inject
	private OrderStatusService statusService;

	@BeforeDeploy
	public void dep() throws Exception {
		ActiveMQSslConnectionFactory amq = new ActiveMQSslConnectionFactory();
		amq.setBrokerURL("ssl://localhost:61617");
		amq.setUserName("shipuser");
		amq.setPassword("shippwd");
		namingMixIn.getInitialContext().bind("ConnectionFactory", amq);
		Properties systemProps = System.getProperties();
		systemProps.put(
				"javax.net.ssl.trustStore", "/home/tturek/workspace-jdbs/switchyard-integration-course/src/main/resources/client-trustStore.jks"
		);
		systemProps.put(
				"javax.net.ssl.trustStorePassword", "mit123*"
		);
		systemProps.put(
				"javax.net.ssl.keyStore", "/home/tturek/workspace-jdbs/switchyard-integration-course/src/main/resources/client-trustStore.jks"
		);
		systemProps.put(
				"javax.net.ssl.keyStorePassword", "mit123*"
		);
		System.setProperties(systemProps);
	}

	@Test
	public void testOrder() throws Exception {
//		testKit.replaceService("ConsumeJMSService");
//		testKit.replaceService("InventoryServicePortType");
//		testKit.replaceService("AccountingResource");

		// TODO Auto-generated method stub
		// initialize your test message
		Order message = new Order();
		Address address = new Address();
		address.setFirstName("Jiri");
		address.setLastName("Novak");
		address.setStreet("Purkynova");
		address.setCity("Brno");
		address.setZipCode("602 00");

		OrderItem item = new OrderItem();
		item.setArticleId(1);
		item.setCount(1);
		item.setUnitPrice(3);

		LinkedList items = new LinkedList();
		items.add(item);
		items.add(item);

		message.setId(1);
		message.setAddress(address);
		message.setItems(items);

		String result = service.operation("order").sendInOut(message)
				.getContent(String.class);

		// validate the results

		Thread.sleep(5000);
		System.out.println("Status: " + statusService.find(1L));

		Assert.assertTrue("Implement me", false);
	}
}
