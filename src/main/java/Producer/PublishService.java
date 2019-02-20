package Producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PublishService {

    private Connection connection;
    private Channel channel;

    public PublishService() {
        ConnectionFactory factory = getFactory();
        try {
            this.connection = factory.newConnection();
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

    public ProducerExchange declareExchange(String exchangeName, String exchangeType, String routingKey)
            throws IOException {
        channel.exchangeDeclare(exchangeName, exchangeType);
        return new ProducerExchange(exchangeName, exchangeType, routingKey, channel);
    }

    public void closeAll() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}