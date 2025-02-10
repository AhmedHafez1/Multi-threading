public class RaceCondition {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Runnable runnableInc = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Runnable runnableDec = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        };

        var threads = new Thread[1_000];

        for (int i = 0; i < 600; i++) {
            threads[i] = new Thread(runnableInc);
        }

        for (int i = 600; i < threads.length ; i++) {
            threads[i] = new Thread((runnableDec));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (int i = 0; i < 1_000; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(counter.getCount());
    }
}
