public class Food {
    private final Object key1 = new Object();
    private final Object key2 = new Object();

    public void cookPizza() {
        synchronized (key1) {
            System.out.println("Cooking Pizza " + Thread.currentThread().getName());
            cookCheese();
        }
    }

    public void cookCheese() {
        synchronized (key2) {
            System.out.println("Cooking Cheese " + Thread.currentThread().getName());
            hotMilk();
        }
    }

    public void hotMilk() {
        synchronized (key1) {
            System.out.println("Hot Milk " + Thread.currentThread().getName());
        }
    }
}
