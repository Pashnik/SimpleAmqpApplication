package Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerService implements Readable {

    private Channel channel;
    private MessageHandler handler;
    private Connection connection;

    public ConsumerService() {
        ConnectionFactory factory = getFactory();
        try {
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            this.handler = new MessageHandler(channel);
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

    @Override
    public void getMessages() throws IOException {
        handler.handleMessages();
    }

    @Override
    public void closeAll() throws IOException, TimeoutException {
        channel.close();
        connection.close();
        handler.close();
    }

}
