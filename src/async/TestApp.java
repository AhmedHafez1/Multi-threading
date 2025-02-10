package async;

public class TestApp {
    public static void main(String[] args) {
        SynchronousTasks.run();
        ExecutorTasks.run();
        CompleteFutureTasks.run();
    }
}
