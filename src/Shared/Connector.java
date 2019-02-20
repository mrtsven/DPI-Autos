package Shared;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Connector {

    private String subject;
    private IListener listener;

    private Connection_Factory connectionFactory = new Connection_Factory(); // connectionFactory
    Session session; // session for creating messages, producers and
    Destination destination;

    MessageConsumer consumer; // for sending messages;

    public Connector(String queueName, IListener listener){
        subject = queueName;
        this.listener = listener;

        try {
            connection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void connection() throws JMSException {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");

        // Moved this factory.
//      ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        Connection connection = connectionFactory.getConnectionFactory().createConnection();
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        destination = session.createTopic(subject);

        consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try{
                    receiveMessage(message);
                } catch (JMSException e){
                    e.printStackTrace();
                }
                // Old piece of code
//                System.out.println("Received message: " + message);
            }
        });
    }

    public void sendMessage(Car message, String channel, String typeMessage) throws JMSException {
        System.out.println("Sending message: " + session);
        Destination producerDestination = session.createTopic(channel);
        MessageProducer messageProducer = session.createProducer(producerDestination);

        ObjectMessage objectMessage = session.createObjectMessage(message);

        messageProducer.send(objectMessage);

        System.out.println("A car " + typeMessage + " has been sent: " + message.toString());
    }

    public void receiveMessage(Message message) throws JMSException {

        if (message instanceof ObjectMessage) {

            ObjectMessage objectMessage = (ObjectMessage) message;
            Car carMessage = (Car) objectMessage.getObject();
//            if (carMessage instanceof  CarReply){
//                System.out.println("REPLY CASE");
//                listener.onMessage(carMessage);
//            }
//            if(carMessage instanceof  CarRequest){
//                System.out.println("REQUEST CASE");
//                listener.onMessage(carMessage);
//            }
            if(carMessage.type != null){
                switch (carMessage.type){
                    case REPLY:
                        System.out.println("REPLY CASE");
                        listener.onMessage(carMessage);
                        break;
                    case REQUEST:
                        System.out.println("REQUEST CASE");
                        listener.onMessage(carMessage);
                        break;
                }
            }
        }
    }
}
