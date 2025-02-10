public class Main {
    public static void main(String[] args) {
        Runnable runnable1 = () ->
                System.out.println("First task Running inside thread " + Thread.currentThread().getName());
        Runnable runnable2 = () ->
                System.out.println("Second task Running inside thread " + Thread.currentThread().getName());


        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.setName("Thread 1");
        thread2.setName("Thread 2");

        thread1.start();
        thread2.start();
    }
}