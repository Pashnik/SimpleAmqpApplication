package Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ConsumerService {

    private Channel channel;
    private Connection connection;

    public ConsumerService() {
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

    public ConsumerExchange declareExchange(String exchangeName, String exchangeType, String routingKey)
            throws IOException {
        channel.exchangeDeclare(exchangeName, exchangeType);
        return new ConsumerExchange(exchangeName, exchangeType, routingKey, channel);
    }

    public ConsumerQueue declareQueue(String name, boolean durable, boolean exclusive, boolean autoDelete,
                                      boolean autoAck, Map<String, Object> args) throws IOException {
        channel.queueDeclare(name, durable, exclusive, autoDelete, args);
        return new ConsumerQueue(name, durable, exclusive, autoDelete, autoAck, args, channel);
    }

    public void bind(ConsumerExchange exchange, ConsumerQueue queue) throws IOException {
        channel.queueBind(queue.getName(), exchange.getName(), exchange.getRoutingKey());
    }

    public void closeAll() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

}
