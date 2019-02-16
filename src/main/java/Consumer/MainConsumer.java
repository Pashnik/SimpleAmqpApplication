package Consumer;

import java.io.IOException;

public class MainConsumer {
    public static void main(String[] args) {
        Readable service = new ConsumerService();
        try {
            service.getMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}