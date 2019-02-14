package Producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Publishable {
    void publishMessage(String message) throws IOException;

    void closeAll() throws IOException, TimeoutException;
}
