package Consumer;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkPool extends Thread {

    private final ExecutorService executorService;
    private final Channel channel;

    public WorkPool(Channel channel) {
        this.channel = channel;
        this.executorService = Executors.newCachedThreadPool();
    }

    public void doTask(String message, long consumerTag) {
        executorService.submit(() -> {
            process(message, content -> System.out.println(content));
            try {
                channel.basicAck(consumerTag, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void closeWorkPool() {
        executorService.shutdown();
    }

    private void process(String message, Worker worker) {
        worker.processContent(message);
    }

}