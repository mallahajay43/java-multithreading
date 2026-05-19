package advanced;

import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    public static void main(String[] args) {
        ReadWriteStamped rw = new ReadWriteStamped(); 
    
        Thread w1 = new Thread(() -> {
            try {
                rw.write(1);
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
        Thread w2 = new Thread(() -> {
            try {
                rw.write(2);
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
        Thread w3 = new Thread(() -> {
            try {
                rw.write(3);
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
    
        Thread r1 = new Thread(() -> {
            try {
                rw.read();
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
        Thread r2 = new Thread(() -> {
            try {
                rw.read();
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
        Thread r3 = new Thread(() -> {
            try {
                rw.read();
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        });
    
        r1.start();
        w1.start();
        r2.start();
        w2.start();
        r3.start();
        w3.start();
    }
}

class ReadWriteStamped {
    StampedLock lock = new StampedLock();

    int value;

    void read() {
        // Try an optimistic lock.
        long stamp = lock.tryOptimisticRead();
        // Get racy value.
        int currentValue = this.value;
        System.out.println("race vlaue: " + currentValue);

        if (lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                currentValue = this.value;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        System.out.println("Read operation performed: " + currentValue);
    }

    void write(int value) {
        long stamp = lock.writeLock();
        try {
            this.value = value;
            System.out.println("Write operation performed: " + this.value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}
