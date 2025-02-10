package async;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CompleteFutureTasks {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        var rand = new Random();

        List<Supplier<Quotation>> quotationTasks = IntStream.rangeClosed(1, 10)
                .mapToObj(x -> {
                    return (Supplier<Quotation>) () -> {
                        try {
                            Thread.sleep(rand.nextInt(200, 210));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        return new Quotation("Quotation " + x, rand.nextInt(100));
                    };
                })
                .toList();


        Instant start = Instant.now();

        List<CompletableFuture<Quotation>> futureList = quotationTasks.stream()
                .map(CompletableFuture::supplyAsync)
                .toList();

        futureList.forEach(futureQuot -> {
            var q = futureQuot.join();
            System.out.println(q.name() + " price: " + q.price());
        });

        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("Duration [ASYNC]: " + duration.toMillis() + " ms");
    }
}
