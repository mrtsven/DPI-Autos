package Shared;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;

public class Connection_Factory {

    ConnectionFactory connectionFactory;

    Connection_Factory(){
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        // Trust all packages for testing purposes.
        ((ActiveMQConnectionFactory) connectionFactory).setTrustAllPackages(true);
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
