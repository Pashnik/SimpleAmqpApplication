package Consumer;

import com.rabbitmq.client.Channel;

public class ConsumerExchange {

    private final String name;
    private final String type;
    private final String routingKey;
    private final Channel channel;

    public ConsumerExchange(String name, String type, String routingKey, Channel channel) {
        this.name = name;
        this.type = type;
        this.routingKey = routingKey;
        this.channel = channel;
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
