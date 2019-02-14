package Producer;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Executor {

    private static final String EXCHANGE_NAME = "simple_exchange";
    private static final String ROUTING_KEY = "route";
    private static final String ROUTING_TYPE = "direct";

    private final Channel channel;

    public Executor(Channel channel) {
        this.channel = channel;
    }

    public void sendMessageToExchange(String message) throws IOException {
        channel.exchangeDeclare(EXCHANGE_NAME, ROUTING_TYPE);
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null,
                message.getBytes(StandardCharsets.UTF_8));
        Logger.getGlobal().info("Message:" + " " + message + " " + "is successfully sent to exchange!");
    }
}
