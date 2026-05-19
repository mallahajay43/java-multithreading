package advanced;

import java.util.concurrent.Semaphore;

public class ResourcePoolSemaphore {
    private final Semaphore semaphore;
    private final boolean[] resources = new boolean[3]; // Pool size of 3

    public ResourcePoolSemaphore() {
        this.semaphore = new Semaphore(3, true); // 3 permits, 'true' for fairness
    }

    public void useResource() {
        try {
            semaphore.acquire(); // Blocks if no permits available
            System.out.println(Thread.currentThread().getName() + " secured a permit.");
            
            // Critical section: simulate work
            Thread.sleep(2000); 
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " releasing permit.");
            semaphore.release(); // Always release in finally block
        }
    }
}