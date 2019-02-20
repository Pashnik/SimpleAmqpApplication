package Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

public class ConsumerQueue {

    private final String name;
    private final boolean durable;
    private final boolean exclusive;
    private final boolean autoDelete;
    private final boolean autoAck;
    private final Map<String, Object> args;
    private final Channel channel;

    public ConsumerQueue(String name, boolean durable, boolean exclusive,
                         boolean autoDelete, boolean autoAck, Map<String, Object> args, Channel channel) {
        this.name = name;
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
        this.autoAck = autoAck;
        this.args = args;
        this.channel = channel;
    }

    public void handleMessages() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            processMessage(message, (content) -> System.out.println(message));
        };
        channel.basicConsume(name, autoAck, deliverCallback, consumerTag ->
                Logger.getGlobal().info("Consumer with consumerTag:" + " " + consumerTag + " " + "is cancelled!"));
    }

    private void processMessage(String message, Worker worker) {
        worker.processContent(message);
    }

    public String getName() {
        return name;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public boolean isAutoAck() {
        return autoAck;
    }
}
