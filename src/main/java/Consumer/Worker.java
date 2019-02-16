package Consumer;

@FunctionalInterface
public interface Worker {
    void processContent(String message);
}
