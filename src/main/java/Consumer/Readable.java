package Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Readable {
    void getMessages() throws IOException;

    void closeAll() throws IOException, TimeoutException;
}
