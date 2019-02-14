package Producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Publisher {
    public static void main(String[] args) {
        Publishable publishService = new PublishService();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                publishService.publishMessage(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                publishService.closeAll();
            } catch (TimeoutException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
