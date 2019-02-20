package Consumer;

import java.io.IOException;

public class MainConsumer {
    public static void main(String[] args) {
        ConsumerService service = new ConsumerService();
        try {
            ConsumerExchange simpleExchange = service.declareExchange("simple_exchange", "direct",
                    "route");
            ConsumerQueue testQueue = service.declareQueue("demo_queue", true, false,
                    false, false, null);
            service.bind(simpleExchange, testQueue);
            testQueue.handleMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}