package Consumer;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.logging.Logger;

public class WorkPool extends Thread {

    private Channel channel;
    private String message;
    private long tag;

    public WorkPool(Channel channel, String message, long tag) {
        this.channel = channel;
        this.message = message;
        this.tag = tag;
    }

    @Override
    public void run() {
        Logger.getGlobal().info("Message:" + " " + message + " " + "is arrived to a work pool!");
        process(message -> System.out.println(message));
        try {
            channel.basicAck(tag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(Worker worker) {
        worker.processContent(message);
    }

}
