package async;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ExecutorTasks {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        var rand = new Random();
        List<Callable<Quotation>> quotationTasks = IntStream.rangeClosed(1, 10)
                .mapToObj(x -> {
                    return (Callable<Quotation>) () -> {
                        Thread.sleep(rand.nextInt(200, 210));
                        return new Quotation("Quotation " + x, rand.nextInt(100));
                    };
                })
                .toList();

        Instant start = Instant.now();

        var executorService = Executors.newCachedThreadPool();

        List<Future<Quotation>> futureQuotations = quotationTasks.stream()
                .map(executorService::submit)
                .toList();

        futureQuotations.forEach(futureQuotation -> {
            try {
                var q = futureQuotation.get();
                System.out.println(q.name() + " price: " + q.price());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("Duration [EX-SER]: " + duration.toMillis() + " ms");
    }
}
