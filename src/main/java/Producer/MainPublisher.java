package Producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class MainPublisher {
    public static void main(String[] args) {
        Publishable service = new PublishService();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                service.publishMessage(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                service.closeAll();
            } catch (TimeoutException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
