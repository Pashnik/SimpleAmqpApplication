package Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerService {

    private Channel channel;

    public ConsumerService() {
        ConnectionFactory factory = getFactory();
        try {
            Connection connection = factory.newConnection();
            this.channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /*
    Configure connection settings with RabbitMQ-server
     */

    private ConnectionFactory getFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConnectionFactory.DEFAULT_HOST);
        factory.setVirtualHost(ConnectionFactory.DEFAULT_VHOST);
        factory.setUsername(ConnectionFactory.DEFAULT_USER);
        factory.setPassword(ConnectionFactory.DEFAULT_PASS);
        factory.setPort(ConnectionFactory.DEFAULT_AMQP_PORT);
        return factory;
    }

    public void getMessages() throws IOException {
        MessageHandler handler = new MessageHandler(channel);
        handler.handleMessages();
    }


}
