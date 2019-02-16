package Consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class MessageHandler {

    private static final String EXCHANGE_NAME = "simple_exchange";
    private static final String EXCHANGE_TYPE = "direct";
    private static final String ROUTING_KEY = "route";
    private static final String QUEUE_NAME = "demo_queue";

    private static final boolean DURABLE = true;
    private static final boolean EXCLUSIVE = false;
    private static final boolean AUTO_DELETE = false;
    private static final boolean AUTO_ACK = false;

    private final Channel channel;
    private final WorkPool workPool;

    public MessageHandler(Channel channel) throws IOException {
        this.channel = channel;
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        channel.queueDeclare(QUEUE_NAME, DURABLE, EXCLUSIVE, AUTO_DELETE, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        this.workPool = new WorkPool(channel);
    }

    public void handleMessages() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            workPool.doTask(message, delivery.getEnvelope().getDeliveryTag());
        };
        channel.basicConsume(QUEUE_NAME, AUTO_ACK, deliverCallback, consumerTag ->
                Logger.getGlobal().info("Consumer with consumerTag:" + consumerTag + "is cancelled!"));
    }

    public void close() {
        workPool.closeWorkPool();
    }
}