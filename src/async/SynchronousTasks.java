package async;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class SynchronousTasks {
    record Quotation(String name, int price) {
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        var rand = new Random();
        var quotationTasks = IntStream.rangeClosed(1, 10)
                .mapToObj(x -> {
                    return (Callable<Quotation>) () -> {
                        Thread.sleep(rand.nextInt(200, 210));
                        return new Quotation("Quotation " + x, rand.nextInt(100));
                    };
                })
                .toList();

        Instant start = Instant.now();

        var quotations = quotationTasks.stream()
                .map(quotationCallable -> {
                    try {
                        return quotationCallable.call();
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }).peek(q -> {
                    if (q != null) {
                        System.out.println("Quotation: " + q.name + " price: " + q.price);
                    }
                })
                .toList();

        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("Duration: " + duration.toMillis() + " ms");
    }
}
