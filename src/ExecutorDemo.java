import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            executor.execute(new PrintTask("Task " + (i + 1)));
        }

        executor.shutdown();

        System.out.println("Main thread ended");
    }

    public static class PrintTask implements Runnable {
        public final String name;
        public final int sleepTime;
        private static final SecureRandom generator = new SecureRandom();

        public PrintTask(String name) {
            this.name = name;
            this.sleepTime = generator.nextInt(5000);
        }

        @Override
        public void run() {
            System.out.println(name + " going to sleep " + sleepTime);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }

            System.out.println(name + " finished the execution");
        }
    }
}
