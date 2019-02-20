package Producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Exchange {

    private final String name;
    private final String type;
    private final String routingKey;
    private final Channel channel;

    public Exchange(String name, String type, String routingKey, Channel channel) {
        this.name = name;
        this.type = type;
        this.routingKey = routingKey;
        this.channel = channel;
    }

    public void publishMessage(String message) throws IOException {
        channel.basicPublish(name, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes(StandardCharsets.UTF_8));
        Logger.getGlobal().info("Message:" + " " + message + " " + "is successfully sent to exchange!");
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
