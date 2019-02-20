package Producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class MainPublisher {
    public static void main(String[] args) {
        PublishService service = new PublishService();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            ProducerExchange simpleExchange =
                    service.declareExchange("simple_exchange", "direct", "route");
            String line;
            while ((line = reader.readLine()) != null) {
                simpleExchange.publishMessage(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                service.closeAll();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
