package com.redhat.brq.integration.switchyard.test;

import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.switchyard.component.test.mixins.naming.NamingMixIn;

import javax.naming.NamingException;

public class JMSUtils {

    private JMSUtils() {
    }

    public static void bindConnectionFactory(NamingMixIn namingMixIn) throws Exception {
        ActiveMQSslConnectionFactory amq = new ActiveMQSslConnectionFactory();
        amq.setBrokerURL("ssl://localhost:61617");
        amq.setUserName("shipuser");
        amq.setPassword("shippwd");
        amq.setKeyStore(System.getProperty("javax.net.ssl.trustStore"));
        amq.setKeyStorePassword(System.getProperty("javax.net.ssl.trustStorePassword"));

        try {
            namingMixIn.getInitialContext().bind("java:jboss/ConnectionFactory", amq);
        } catch (NamingException e) {
            // ignore
        }
    }
}
