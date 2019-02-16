package Producer;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Executor {

    private static final String EXCHANGE_NAME = "simple_exchange";
    private static final String EXCHANGE_TYPE = "direct";
    private static final String ROUTING_KEY = "route";

    private final Channel channel;

    public Executor(Channel channel) throws IOException {
        this.channel = channel;
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
    }

    public void sendMessageToExchange(String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null,
                message.getBytes(StandardCharsets.UTF_8));
        Logger.getGlobal().info("Message:" + " " + message + " " + "is successfully sent to exchange!");
    }
}
