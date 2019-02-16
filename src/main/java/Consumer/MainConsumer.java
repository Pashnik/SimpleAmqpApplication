package Consumer;

import java.io.IOException;

public class MainConsumer {
    public static void main(String[] args) {
        Readable consumerService = new ConsumerService();
        try {
            consumerService.getMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
