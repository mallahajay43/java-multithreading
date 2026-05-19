package advanced;

public class ProducerConsumerSimple {
    
    public static void main(String[] args) {
        Box box = new Box();
    
        Thread t1 = new Thread(() -> {
            for (int i=0; i<5; i++) {
                try {
                    box.produce(i);
                } catch (Exception e) {}
            }
        });
    
        Thread t2 = new Thread(() -> {
            for (int i=0; i<5; i++) {
                try {
                    box.consume();
                } catch (InterruptedException e) {}
            }
        });
    
        t1.start();
        t2.start();
    }
}

/**
 * Working
wait() → thread goes into waiting state
notify() → wakes one waiting thread
notifyAll() → wakes all waiting threads
 */

class Box {
    
    int value;
    boolean produced = false;

    synchronized void produce(int value) throws InterruptedException {
        while (produced) {
            wait();
        }
        this.value = value;
        produced = true;
        // notify() -> this method randomally awakes one thread present in current threads wating queue, hence causes starvation issue.
        // notifyAll() -> awakes all the  
        notifyAll();
    }

    synchronized void consume() throws InterruptedException {
        if (!produced) {
            wait();
        }
        System.out.println(this.value);
        produced = false;
        notifyAll();
    }
}
