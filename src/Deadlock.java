public class Deadlock {
    public static void main(String[] args) {
        var food = new Food();

        Thread t1 = new Thread(food::cookPizza);
        Thread t2 = new Thread(food::cookCheese);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
